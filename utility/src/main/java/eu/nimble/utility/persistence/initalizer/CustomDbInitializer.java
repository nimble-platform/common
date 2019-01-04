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

/**
 * This component checks whether the binary_content table is available in the binarycontentdb during the initialization
 * of the service. If not, the table is created.
 *
 * Created by suat on 19-Dec-18.
 */
@Component
@Profile("!local_dev")
public class CustomDbInitializer {

    private static final String QUERY_CREATE_RESOURCE_TABLE =
            "CREATE TABLE public.resource (" +
                    "catalogue_repository varchar(255) NULL," +
                    "entity_id int8 NULL," +
                    "party_id varchar(255) NULL," +
                    "user_id varchar(255) NULL," +
                    "CONSTRAINT resource_primary_key PRIMARY KEY (entity_id,catalogue_repository)" +
                    ")" +
                    "WITH (OIDS=FALSE);";

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
            initializeResourceTable();
        }
    }

    private void initializeResourceTable() {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = ubldbDataSource.getConnection();
            s = c.createStatement();
            rs = s.executeQuery(String.format(QUERY_TABLE_SQL, "resource"));

            if(rs.next()) {
                String tableName = rs.getString(1);
                if(tableName == null) {
                    s.execute(QUERY_CREATE_RESOURCE_TABLE);
                    logger.info("created resource table in the ubl database");
                } else {
                    logger.info("resource table is already available in the ubl database");
                }
            }

        } catch (SQLException e) {
            String msg = "Failed to initialize resource table";
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            closeResources(c, s, rs);
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
