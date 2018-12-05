package eu.nimble.utility.persistence.binary;

import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by suat on 03-Dec-18.
 */
@Component
public class BinaryContentService {
    private static final String TABLE_NAME = "binary_content";
    private static final String COLUMN_NAME_URI = "uri";
    private static final String COLUMN_NAME_MIME_CODE = "mime_code";
    private static final String COLUMN_NAME_FILE_NAME = "file_name";
    private static final String COLUMN_NAME_VALUE = "value_";
    private static final String QUERY_INSERT_CONTENT = "INSERT INTO  " + TABLE_NAME + "( " + COLUMN_NAME_URI + "," + COLUMN_NAME_MIME_CODE + "," + COLUMN_NAME_FILE_NAME + "," + COLUMN_NAME_VALUE + ") VALUES (?,?,?,?)";
    private static final String QUERY_SELECT_CONTENT_BY_URI = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_URI + " = ?";
    private static final String QUERY_DELETE_CONTENT_BY_URI = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_URI + " = ?";

    private static Logger logger = LoggerFactory.getLogger(BinaryContentService.class);

    @Autowired
    @Qualifier("binarycontentdbDataSource")
    private DataSource dataSource;

    public void createContent(BinaryObjectType binaryObjectType) {
        Connection c = null;
        PreparedStatement ps = null;

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

    private void closeResources(Connection c, PreparedStatement ps, ResultSet rs, String msg) {
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

