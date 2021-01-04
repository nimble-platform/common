package eu.nimble.utility.persistence.initalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * This component checks whether the binary_content table is available in the binarycontentdb during the initialization
 * of the service. If not, the table is created.
 *
 * Created by suat on 19-Dec-18.
 */
@Component
@Profile("!test")
public class CustomDbInitializer {

    private static final String QUERY_ADD_DEMAND_SEARCH_INDEX_COLUMN = "ALTER TABLE demand_type ADD COLUMN search_index TSVECTOR";
    private static final String QUERY_DEMAND_SEARCH_COLUMN_EXISTS =
            "SELECT * FROM information_schema.columns" +
            " WHERE table_name='demand_type' AND column_name='search_index';";
    private static final String QUERY_GET_EXISTING_INDICES = "SELECT indexname FROM pg_indexes";

    // map of index names mapping to index creation query
    // new indices defined in the same way will be created automatically, when the service is restarted
    private static final Map<String, String> indices = new HashMap<>();
    private static final String INDEX_NAME_METADATA_CREATION_DATE = "metadata_creation_date";
    private static final String INDEX_NAME_METADATA_MODIFICATION_DATE = "metadata_modification_date";
    private static final String INDEX_NAME_METADATA_OWNER_COMPANY = "metadata_owner_company";
    private static final String INDEX_NAME_DEMAND_SEARCH_INDEX = "demand_search_index";
    private static final String INDEX_NAME_DEMAND_DUE_DATE = "demand_due_date";
    private static final String INDEX_NAME_DEMAND_BUYER_COUNTRY = "demand_buyer_country";
    private static final String INDEX_NAME_DEMAND_DELIVERY_COUNTRY = "demand_delivery_country";
    private static final String INDEX_NAME_DEMAND_METADATA_REF = "demand_metadata_ref";
    private static final String INDEX_NAME_CATALOGUE_ID = "catalogue_id";
    private static final String INDEX_NAME_CATALOGUE_UUID = "catalogue_uuid";
    private static final String INDEX_NAME_CATALOGUE_PROVIDER_PARTY = "catalogue_provider_party";
    private static final String INDEX_NAME_CATALOGUE_PERMITTED_PARTIES = "catalogue_permitted_party";
    private static final String INDEX_NAME_CATALOGUE_RESTRICTED_PARTIES = "catalogue_restricted_party";
    private static final String INDEX_NAME_PARTY_IDENTIFICATION_PARTY_REF = "party_identification_party_ref";
    private static final String INDEX_NAME_CATALOGUE_LINE_ID = "catalogue_line_id";
    private static final String INDEX_NAME_CATALOGUE_LINE_CATALOGUE_REF = "catalogue_line_catalogue_ref";
    private static final String INDEX_NAME_COMMODITY_CLASSIFICATION_ITEM_CODE = "commodity_classification_item_code";
    private static final String INDEX_NAME_TEXT_PARTY_INDUSTRY_SECTOR = "text_party_industry_sector";
    private static final String INDEX_NAME_TEXT_PARTY_DESCRIPTION = "text_party_description";
    private static final String INDEX_NAME_TEXT_PARTY_BRAND_NAME = "text_party_brand_name";
    private static final String INDEX_NAME_TEXT_CLAUSE_CONTENT = "text_clause_content";
    private static final String INDEX_NAME_TEXT_CLAUSE_TITLE = "text_clause_title";
    private static final String INDEX_NAME_TEXT_ITEM_PROPERTY_VALUE = "text_item_property_value";
    private static final String INDEX_NAME_TEXT_ITEM_PROPERTY_NAME = "text_item_property_name";
    private static final String INDEX_NAME_TEXT_ITEM_NAME = "text_item_name";
    private static final String INDEX_NAME_TEXT_ITEM_DESCRIPTION = "text_item_description";
    private static final String INDEX_NAME_TEXT_MULTI_TYPE_VALUE = "text_multi_type_value";
    private static final String INDEX_NAME_TEXT_DEMAND_TITLE = "text_demand_title";
    private static final String INDEX_NAME_TEXT_DEMAND_DESCRIPTION = "text_demand_description";
    private static final String INDEX_NAME_CODE_URI = "code_uri";
    private static final String INDEX_NAME_CODE_DEMAND_REF = "code_demand_ref";

    static {
        indices.put(INDEX_NAME_METADATA_CREATION_DATE, String.format("CREATE INDEX %s ON metadata_type(creation_date_item)", INDEX_NAME_METADATA_CREATION_DATE));
        indices.put(INDEX_NAME_METADATA_MODIFICATION_DATE, String.format("CREATE INDEX %s ON metadata_type(modification_date_item)", INDEX_NAME_METADATA_MODIFICATION_DATE));
        indices.put(INDEX_NAME_METADATA_OWNER_COMPANY, String.format("CREATE INDEX %s on metadata_type_owner_company__0(owner_company_items_metadata_0, item)", INDEX_NAME_METADATA_OWNER_COMPANY));
        indices.put(INDEX_NAME_DEMAND_SEARCH_INDEX, String.format("CREATE INDEX %s ON demand_type USING gin(search_index)", INDEX_NAME_DEMAND_SEARCH_INDEX));
        indices.put(INDEX_NAME_DEMAND_DUE_DATE, String.format("CREATE INDEX %s ON demand_type(due_date_item)", INDEX_NAME_DEMAND_DUE_DATE));
        indices.put(INDEX_NAME_DEMAND_BUYER_COUNTRY, String.format("CREATE INDEX %s ON demand_type(buyer_country_demand_type_hj_0)", INDEX_NAME_DEMAND_BUYER_COUNTRY));
        indices.put(INDEX_NAME_DEMAND_DELIVERY_COUNTRY, String.format("CREATE INDEX %s ON demand_type(delivery_country_demand_type_0)", INDEX_NAME_DEMAND_DELIVERY_COUNTRY));
        indices.put(INDEX_NAME_DEMAND_METADATA_REF, String.format("CREATE INDEX %s ON demand_type(metadata_demand_type_hjid)", INDEX_NAME_DEMAND_METADATA_REF));
        indices.put(INDEX_NAME_CATALOGUE_ID, String.format("CREATE INDEX %s ON catalogue_type(id)", INDEX_NAME_CATALOGUE_ID));
        indices.put(INDEX_NAME_CATALOGUE_UUID, String.format("CREATE INDEX %s ON catalogue_type(uuid)", INDEX_NAME_CATALOGUE_UUID));
        indices.put(INDEX_NAME_CATALOGUE_PROVIDER_PARTY, String.format("CREATE INDEX %s ON catalogue_type(provider_party_catalogue_typ_0)", INDEX_NAME_CATALOGUE_PROVIDER_PARTY));
        indices.put(INDEX_NAME_CATALOGUE_PERMITTED_PARTIES, String.format("CREATE INDEX %s ON catalogue_type_permitted_par_0(permitted_party_iditems_cata_0, item)", INDEX_NAME_CATALOGUE_PERMITTED_PARTIES));
        indices.put(INDEX_NAME_CATALOGUE_RESTRICTED_PARTIES, String.format("CREATE INDEX %s ON catalogue_type_restricted_pa_0(restricted_party_iditems_cat_0, item)", INDEX_NAME_CATALOGUE_RESTRICTED_PARTIES));
        indices.put(INDEX_NAME_PARTY_IDENTIFICATION_PARTY_REF, String.format("CREATE INDEX %s ON party_identification_type(party_identification_party_t_0, id)", INDEX_NAME_PARTY_IDENTIFICATION_PARTY_REF));
        indices.put(INDEX_NAME_CATALOGUE_LINE_ID, String.format("CREATE INDEX %s ON catalogue_line_type(id)", INDEX_NAME_CATALOGUE_LINE_ID));
        indices.put(INDEX_NAME_CATALOGUE_LINE_CATALOGUE_REF, String.format("CREATE INDEX %s ON catalogue_line_type(catalogue_line_catalogue_typ_0)", INDEX_NAME_CATALOGUE_LINE_CATALOGUE_REF));
        indices.put(INDEX_NAME_COMMODITY_CLASSIFICATION_ITEM_CODE, String.format("CREATE INDEX %s ON commodity_classification_type(commodity_classification_ite_0, item_classification_code_com_0)", INDEX_NAME_COMMODITY_CLASSIFICATION_ITEM_CODE));
        indices.put(INDEX_NAME_TEXT_PARTY_INDUSTRY_SECTOR, String.format("CREATE INDEX %s ON text_type(industry_sector_party_type_h_0)", INDEX_NAME_TEXT_PARTY_INDUSTRY_SECTOR));
        indices.put(INDEX_NAME_TEXT_PARTY_DESCRIPTION, String.format("CREATE INDEX %s ON text_type(description_party_type_hjid)", INDEX_NAME_TEXT_PARTY_DESCRIPTION));
        indices.put(INDEX_NAME_TEXT_PARTY_BRAND_NAME, String.format("CREATE INDEX %s ON text_type(brand_name_party_type_hjid)", INDEX_NAME_TEXT_PARTY_BRAND_NAME));
        indices.put(INDEX_NAME_TEXT_CLAUSE_CONTENT, String.format("CREATE INDEX %s ON text_type(content_clause_type_hjid)", INDEX_NAME_TEXT_CLAUSE_CONTENT));
        indices.put(INDEX_NAME_TEXT_CLAUSE_TITLE, String.format("CREATE INDEX %s ON text_type(clause_title_clause_type_hjid)", INDEX_NAME_TEXT_CLAUSE_TITLE));
        indices.put(INDEX_NAME_TEXT_ITEM_PROPERTY_VALUE, String.format("CREATE INDEX %s ON text_type(value__item_property_type_hj_0)", INDEX_NAME_TEXT_ITEM_PROPERTY_VALUE));
        indices.put(INDEX_NAME_TEXT_ITEM_PROPERTY_NAME, String.format("CREATE INDEX %s ON text_type(name__item_property_type_hjid)", INDEX_NAME_TEXT_ITEM_PROPERTY_NAME));
        indices.put(INDEX_NAME_TEXT_ITEM_NAME, String.format("CREATE INDEX %s ON text_type(name__item_type_hjid)", INDEX_NAME_TEXT_ITEM_NAME));
        indices.put(INDEX_NAME_TEXT_ITEM_DESCRIPTION, String.format("CREATE INDEX %s ON text_type(description_item_type_hjid)", INDEX_NAME_TEXT_ITEM_DESCRIPTION));
        indices.put(INDEX_NAME_TEXT_MULTI_TYPE_VALUE, String.format("CREATE INDEX %s ON text_type(value__multi_type_value_type_0)", INDEX_NAME_TEXT_MULTI_TYPE_VALUE));
        indices.put(INDEX_NAME_TEXT_DEMAND_TITLE, String.format("CREATE INDEX %s ON text_type(title_demand_type_hjid)", INDEX_NAME_TEXT_DEMAND_TITLE));
        indices.put(INDEX_NAME_TEXT_DEMAND_DESCRIPTION, String.format("CREATE INDEX %s ON text_type(description_demand_type_hjid)", INDEX_NAME_TEXT_DEMAND_DESCRIPTION));
        indices.put(INDEX_NAME_CODE_URI, String.format("CREATE INDEX %s ON code_type(uri)", INDEX_NAME_CODE_URI));
        indices.put(INDEX_NAME_CODE_DEMAND_REF, String.format("CREATE INDEX %s ON code_type(item_classification_code_dem_0)", INDEX_NAME_CODE_DEMAND_REF));
    }

    private static final String QUERY_CREATE_BINARY_CONTENT_TABLE =
            "CREATE TABLE public.binary_content (" +
                    "id varchar(2048) NOT NULL," +
                    "mime_code varchar(255) NULL," +
                    "file_name text NULL," +
                    "value_ bytea NULL," +
                    "CONSTRAINT binary_content_pkey PRIMARY KEY (id)" +
                    ")" +
                    "WITH ( OIDS=FALSE) ;";

    private static final String QUERY_TABLE_SQL = "SELECT to_regclass('%s')";

    private static Logger logger = LoggerFactory.getLogger(CustomDbInitializer.class);

    @Autowired
    @Qualifier("binarycontentdbDataSource")
    private DataSource binaryContentDataSource;

    @Autowired(required = false)
    @Qualifier("ubldbDataSource")
    private DataSource ubldbDataSource;

    @PostConstruct
    public void initialize() {
        initializeBinaryContentDb();
        if(ubldbDataSource != null) {
            initializeDemandIndexColumn();
            createIndices();
        }
    }

    private void initializeDemandIndexColumn() {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = ubldbDataSource.getConnection();
            s = c.createStatement();
            rs = s.executeQuery(QUERY_DEMAND_SEARCH_COLUMN_EXISTS);

            if(!rs.next()) {
                s.execute(QUERY_ADD_DEMAND_SEARCH_INDEX_COLUMN);
                logger.info("Added DemandType search_index column");
            } else {
                logger.info("DemandType search_index column already exists");
            }

        } catch (SQLException e) {
            String msg = "Failed to initialize resource table";
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            closeResources(c, s, rs);
        }
    }

    private void createIndices() {
        // get existing indices
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        Set<String> indexNames = new HashSet<>();
        try {
            c = ubldbDataSource.getConnection();
            s = c.createStatement();
            rs = s.executeQuery(QUERY_GET_EXISTING_INDICES);
            while (rs.next()) {
                indexNames.add(rs.getString("indexname"));
            }

            logger.info("Retrieved existing indices");

        } catch (SQLException e) {
            String msg = "Failed to get existing index names";
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            closeResources(c, s, rs);
        }

        // create indices for the new ones
        try {
            Connection finalC = ubldbDataSource.getConnection();
            indices.entrySet().stream().filter(entry -> !indexNames.contains(entry.getKey())).forEach(newEntry -> {
                Statement st = null;
                try {
                    st = finalC.createStatement();
                    st.execute(newEntry.getValue());
                    logger.info("Created new index {}", newEntry.getKey());
                } catch (SQLException e) {
                    String msg = String.format("Failed to get create index %s", newEntry.getKey());
                    logger.error(msg, e);
                    throw new RuntimeException(msg, e);
                } finally {
                    closeResources(null, st, null);
                }
            });

            logger.info("New index creation completed.");
        } catch (SQLException e) {
            String msg = String.format("Failed to get create indices");
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            closeResources(c, null, null);
        }
    }

    private void initializeBinaryContentDb() {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = binaryContentDataSource.getConnection();
            s = c.createStatement();
            rs = s.executeQuery(String.format(QUERY_TABLE_SQL, "binary_content"));

            if(rs.next()) {
                String tableName = rs.getString(1);
                if(tableName == null) {
                    s.execute(QUERY_CREATE_BINARY_CONTENT_TABLE);
                    logger.info("created binary_content table in the binary content database");
                } else {
                    logger.info("binary_content table is already available in the binary content database");
                }
            }

        } catch (SQLException e) {
            String msg = "Failed to initialize binary_content table";
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            closeResources(c, s, rs);
        }
    }

    private void closeResources(Connection c, Statement ps, ResultSet rs) {
        if (c != null) {
            try {
                if (!c.isClosed()) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close connection", e);
            }
        }
        if (ps != null) {
            try {
                if (!ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close statement", e);
            }
        }
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close result set", e);
            }
        }
    }
}
