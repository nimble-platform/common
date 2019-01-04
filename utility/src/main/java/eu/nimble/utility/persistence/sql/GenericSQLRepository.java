package eu.nimble.utility.persistence.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by suat on 04-Jan-19.
 */
public class GenericSQLRepository {
    private static Logger logger = LoggerFactory.getLogger(GenericSQLRepository.class);

    public static DBResourceBundle executeQueryWithPreparedStatement(DataSource dataSource, String query, List<Object> parameterValues) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs;

        try {
            c = dataSource.getConnection();
            ps = prepareStatement(c, query, parameterValues);
            rs = ps.executeQuery();
            return new DBResourceBundle(c, ps, null, rs);


        } catch (Exception e) {
            String msg = String.format("Failed to insert values for query: %s, values: %s", query, parameterValues != null ? Arrays.toString(parameterValues.toArray()) : "");
            logger.error(msg, e);
            closeResources(c, ps, null, null);
            throw new RuntimeException(msg, e);
        }
    }

    public static DBResourceBundle executeQueryWithStatement(DataSource dataSource, String query) {
        Connection c = null;
        Statement s = null;
        ResultSet rs;

        try {
            c = dataSource.getConnection();
            s = c.createStatement();
            rs = s.executeQuery(query);
            return new DBResourceBundle(c, null, s, rs);


        } catch (Exception e) {
            String msg = String.format("Failed to insert values for query: %s", query);
            logger.error(msg, e);
            closeResources(c, null, s, null);
            throw new RuntimeException(msg, e);
        }
    }

    public static int executeUpdate(DataSource dataSource, String query, List<Object> parameterValues) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = prepareStatement(c, query, parameterValues);
            int queryResult = ps.executeUpdate();
            return queryResult;

        } catch (Exception e) {
            String msg = String.format("Failed to insert values for query: %s, values: %s", query, parameterValues != null ? Arrays.toString(parameterValues.toArray()) : "");
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, null, null);
        }
    }

    private static PreparedStatement prepareStatement(Connection connection, String query, List<Object> parameterValues) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        if(parameterValues != null && parameterValues.size() > 0) {
            for (int i = 0; i < parameterValues.size(); i++) {
                ps.setObject(i + 1, parameterValues.get(i));
            }
        }
        return ps;
    }

    public static void closeResources(DBResourceBundle dbResourceBundle) {
        if(dbResourceBundle != null) {
            closeResources(dbResourceBundle.getConnection(), dbResourceBundle.getPreparedStatement(), dbResourceBundle.getStatement(), dbResourceBundle.getResultSet());
        }
    }

    public static void closeResources(Connection c, PreparedStatement ps, Statement s, ResultSet rs) {
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
                logger.warn("Failed to close prepared statement", e);
            }
        }
        if (s != null) {
            try {
                if (!s.isClosed()) {
                    s.close();
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
