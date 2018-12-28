package eu.nimble.utility.persistence.binary;

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
public class BinaryContentDbInitializerForH2Db {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE binary_content (" +
                    "id varchar(2048) NOT NULL," +
                    "mime_code varchar(255) NULL," +
                    "file_name text NULL," +
                    "value_ blob NULL," +
                    "PRIMARY KEY (id)" +
                    ")";

    private static Logger logger = LoggerFactory.getLogger(BinaryContentDbInitializerForH2Db.class);

    @Autowired
    @Qualifier("binarycontentdbDataSource")
    private DataSource binaryContentDataSource;

    @PostConstruct
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
                s.execute(CREATE_TABLE_SQL);
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
