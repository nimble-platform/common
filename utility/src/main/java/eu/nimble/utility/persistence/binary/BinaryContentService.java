package eu.nimble.utility.persistence.binary;

import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.UUID;

/**
 * Service to manage data in the binary content database.
 *
 * Created by suat on 03-Dec-18.
 */
@Component
public class BinaryContentService {
    private static final String TABLE_NAME = "binary_content";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_MIME_CODE = "mime_code";
    private static final String COLUMN_NAME_FILE_NAME = "file_name";
    private static final String COLUMN_NAME_VALUE = "value_";
    private static final String QUERY_INSERT_CONTENT = "INSERT INTO  " + TABLE_NAME + "( " + COLUMN_NAME_ID + "," + COLUMN_NAME_MIME_CODE + "," + COLUMN_NAME_FILE_NAME + "," + COLUMN_NAME_VALUE + ") VALUES (?,?,?,?)";
    private static final String QUERY_SELECT_CONTENT_BY_URI = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";
    private static final String QUERY_DELETE_CONTENT_BY_URI = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE public.binary_content (" +
                    "id varchar(2048) NOT NULL," +
                    "mime_code varchar(255) NULL," +
                    "file_name text NULL," +
                    "value_ bytea NULL," +
                    "CONSTRAINT binary_content_pkey PRIMARY KEY (id)" +
                    ")" +
                    "WITH ( OIDS=FALSE) ;";
    private static final String QUERY_TABLE_SQL = "SELECT to_regclass('binary_content')";

    private static Logger logger = LoggerFactory.getLogger(BinaryContentService.class);

    @Autowired
    @Qualifier("binarycontentdbDataSource")
    private DataSource dataSource;

    @Value("${nimble.binary-content.url}")
    private String binaryContentUrl;

    public BinaryObjectType createContent(BinaryObjectType binaryObjectType) {
        Connection c = null;
        PreparedStatement ps = null;

        if(binaryObjectType.getUri() == null || !binaryObjectType.getUri().startsWith(binaryContentUrl)) {
            binaryObjectType.setUri(binaryContentUrl + UUID.randomUUID().toString());
        }

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement(QUERY_INSERT_CONTENT);
            ps.setString(1, binaryObjectType.getUri());
            ps.setString(2, binaryObjectType.getMimeCode());
            ps.setString(3, binaryObjectType.getFileName());
            ps.setBytes(4, binaryObjectType.getValue());
            int queryResult = ps.executeUpdate();
            ps.close();

            if (queryResult > 0) {
                logger.info("Binary content created with uri: {}", binaryObjectType.getUri());
            } else {
                logger.warn("Failed to created binary content for uri: {}, file name: {}, mime type: {}", binaryObjectType.getUri(), binaryObjectType.getFileName(), binaryObjectType.getMimeCode());
            }

            return binaryObjectType;

        } catch (SQLException e) {
            String msg = String.format("Failed to retrieve binary content for uri: %s", binaryObjectType.getUri());
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, null, String.format("While getting binary content for uri: %s", binaryObjectType.getUri()));
        }
    }

    public BinaryObjectType retrieveContent(String uri) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement(QUERY_SELECT_CONTENT_BY_URI);
            ps.setString(1, uri);
            rs = ps.executeQuery();
            InputStream content = null;
            BinaryObjectType result = null;
            if (rs.next()) {
                result = new BinaryObjectType();
                result.setMimeCode(rs.getString(COLUMN_NAME_MIME_CODE));
                result.setValue(rs.getBytes(COLUMN_NAME_VALUE));
                result.setUri(uri);
                result.setFileName(rs.getString(COLUMN_NAME_FILE_NAME));
            }
            rs.close();
            ps.close();

            return result;

        } catch (SQLException e) {
            String msg = String.format("Failed to retrieve binary content for uri: %s", uri);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, rs, String.format("While getting binary content for uri: %s", uri));
        }
    }

    public void deleteContent(String uri) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement(QUERY_DELETE_CONTENT_BY_URI);
            ps.setString(1, uri);
            int queryResult = ps.executeUpdate();

            if (queryResult > 0) {
                logger.info("Binary content deleted with uri: {}", uri);
            } else {
                logger.warn("Failed to delete binary content for uri: {}", uri);
            }

            ps.close();

        } catch (SQLException e) {
            String msg = String.format("Failed to delete binary content for uri: %s", uri);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, null, String.format("While deleting binary content for uri: %s", uri));
        }
    }

    @PostConstruct
    public void initializeBinaryContentDb() {
        Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        try {
            c = dataSource.getConnection();
            s = c.createStatement();
            rs = s.executeQuery(QUERY_TABLE_SQL);

            if(rs.next()) {
                String tableName = rs.getString(1);
                if(tableName == null) {
                    s.execute(CREATE_TABLE_SQL);
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
            closeResources(c, s, rs, "");
        }
    }

    private void closeResources(Connection c, Statement ps, ResultSet rs, String msg) {
        if (c != null) {
            try {
                if (!c.isClosed()) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close connection: {}", msg, e);
            }
        }
        if (ps != null) {
            try {
                if (!ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close prepared statement: {}", msg, e);
            }
        }
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close result set: {}", msg, e);
            }
        }
    }
}

