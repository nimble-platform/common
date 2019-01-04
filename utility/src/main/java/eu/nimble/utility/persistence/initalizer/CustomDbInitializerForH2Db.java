package eu.nimble.utility.persistence.initalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;

/**
 * This component initializes the binary content database in the in-memory H2 database. It is only activated in the
 * local_dev profile.
 *
 * Created by suat on 19-Dec-18.
 */
@Component
@Profile("local_dev")
public class CustomDbInitializerForH2Db {

    private static final String QUERY_CREATE_RESOURCE_TABLE =
            "CREATE TABLE resource (" +
                    "catalogue_repository varchar(255) NOT NULL," +
                    "entity_id int8 NOT NULL," +
                    "party_id varchar(255) NOT NULL," +
                    "user_id varchar(255) NULL," +
                    "PRIMARY KEY (entity_id,catalogue_repository)" +
                    ")";

    private static final String QUERY_CREATE_BINARY_CONTENT_TABLE =
            "CREATE TABLE binary_content (" +
                    "id varchar(2048) NOT NULL," +
                    "mime_code varchar(255) NULL," +
                    "file_name text NULL," +
                    "value_ blob NULL," +
                    "PRIMARY KEY (id)" +
                    ")";

    private static Logger logger = LoggerFactory.getLogger(CustomDbInitializerForH2Db.class);

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

            DatabaseMetaData dbm = c.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "resource", null);
            if (tables.next()) {
                logger.info("resource table is already available in the ubl database");
            } else {
                s = c.createStatement();
                s.execute(QUERY_CREATE_RESOURCE_TABLE);
                logger.info("created resource table in the ubl database");
            }

        } catch (SQLException e) {
            String msg = "Failed to initialize resource table";
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        } finally {
            closeResources(c, s, rs);
        }
    }

    public void initializeBinaryContentDb() {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = binaryContentDataSource.getConnection();

            DatabaseMetaData dbm = c.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "binary_content", null);
            if (tables.next()) {
                logger.info("binary_content table is already available in the binary content database");
            } else {
                s = c.createStatement();
                s.execute(QUERY_CREATE_BINARY_CONTENT_TABLE);
                logger.info("created binary_content table in the binary content database");
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
