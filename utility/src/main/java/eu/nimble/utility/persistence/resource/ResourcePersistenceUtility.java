package eu.nimble.utility.persistence.resource;

import eu.nimble.utility.CommonSpringBridge;
import eu.nimble.utility.persistence.sql.DBResourceBundle;
import eu.nimble.utility.persistence.sql.GenericSQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.*;

/**
 * Created by suat on 10-Dec-18.
 */
public class ResourcePersistenceUtility {

    /**
     * The SQL query below is completed in the {@code insertHjidsForObject} method
     */
    private static final String QUERY_INSERT_RESOURCES = "INSERT INTO resource (catalogue_repository, entity_id, party_id, user_id) VALUES %s";
    /**
     * The SQL query below is completed in the {@code deleteEntityIdsForObject} method
     */
    private static final String QUERY_DELETE_RESOURCES = "DELETE FROM resource WHERE catalogue_repository = ? AND entity_id IN (%s)";
    /**
     * The SQL query below is completed in the {@code hjidsBelongsToParty} method
     */
    private static final String QUERY_GET_RESOURCES = "SELECT entity_id, party_id FROM resource WHERE catalogue_repository = ? AND entity_id IN (%s)";
    private static final String QUERY_GET_ALL_RESOURCES = "SELECT catalogue_repository, entity_id, party_id, user_id FROM resource";

    private static Logger logger = LoggerFactory.getLogger(ResourcePersistenceUtility.class);

    public static List<Resource> getAllResources() {
        DBResourceBundle dbResourceBundle = null;
        try {
            dbResourceBundle = GenericSQLRepository.executeQueryWithPreparedStatement(CommonSpringBridge.getInstance().getUbldbDataSource(), QUERY_GET_ALL_RESOURCES, null);
            ResultSet rs = dbResourceBundle.getResultSet();
            List<Resource> resources = new ArrayList<>();
            while (rs.next()) {
                Resource res = new Resource();
                res.setPartyId(rs.getString("party_id"));
                res.setEntityId(rs.getLong("entity_id"));
                res.setRepositoryName(rs.getString("catalogue_repository"));
                res.setUserId(rs.getString("user_id"));
                resources.add(res);
            }

            return resources;

        } catch (Exception e) {
            String msg = String.format("Failed to retrieve entity ids", QUERY_GET_ALL_RESOURCES, new Object[]{});
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            GenericSQLRepository.closeResources(dbResourceBundle);
        }
    }

    public static List<Resource> getResourcesForIds(String catalogueRepository, Set<Long> hjids) {
        StringBuilder getQueryConditions = new StringBuilder("");
        List<Object> values = new ArrayList<>();
        values.add(catalogueRepository);

        for(Long hjid : hjids) {
            getQueryConditions.append("?,");
            values.add(hjid);
        }

        String query = getQueryConditions.toString();
        query = String.format(QUERY_GET_RESOURCES, query.substring(0, query.length() - 1));

        List<Resource> resources = new ArrayList<>();
        DBResourceBundle dbResourceBundle = null;
        try {
            dbResourceBundle = GenericSQLRepository.executeQueryWithPreparedStatement(CommonSpringBridge.getInstance().getUbldbDataSource(), query, values);
            ResultSet rs = dbResourceBundle.getResultSet();
            while (rs.next()) {
                Resource res = new Resource();
                res.setPartyId(rs.getString("party_id"));
                res.setEntityId(rs.getLong("entity_id"));
                resources.add(res);
            }

            return resources;

        } catch (Exception e) {
            String msg = String.format("Failed to retrieve entity ids", query, Arrays.toString(values.toArray()));
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            GenericSQLRepository.closeResources(dbResourceBundle);
        }
    }

    public static void insertResourcesForParty(String catalogueRepository, String partyId, Set<Long> hjids) {
        StringBuilder insertQueryBuilder = new StringBuilder("");
        List<Object> values = new ArrayList<>();

        int entityCount = 0;
        for (Long hjid : hjids) {
            insertQueryBuilder.append("(?,?,?,?),");
            values.add(catalogueRepository);
            values.add(hjid);
            values.add(partyId);
            values.add(null);

            entityCount++;
            // insert rows in 1000 of chunks as it is said the maximum number of rows for one values statement is 1000.
            if (entityCount % 1000 == 0) {
                // remove the comma at the end
                String query = insertQueryBuilder.toString();
                query = String.format(QUERY_INSERT_RESOURCES, query.substring(0, query.length() - 1));

                // execute insert query
                GenericSQLRepository.executeUpdate(CommonSpringBridge.getInstance().getUbldbDataSource(), query, values);

                // reset the values
                insertQueryBuilder = new StringBuilder("");
                values = new ArrayList<>();
            }
        }

        // insert the non-inserted rows
        if (entityCount % 1000 != 0) {
            String query = insertQueryBuilder.toString();
            query = String.format(QUERY_INSERT_RESOURCES, query.substring(0, query.length() - 1));
            GenericSQLRepository.executeUpdate(CommonSpringBridge.getInstance().getUbldbDataSource(), query, values);
        }
    }

    public static void deleteResourcesForParty(String catalogueRepository, Set<Long> hjids) {
        StringBuilder deleteQueryConditions = new StringBuilder("");
        List<Object> values = new ArrayList<>();
        values.add(catalogueRepository);

        for(Long hjid : hjids) {
            deleteQueryConditions.append("?,");
            values.add(hjid);
        }

        String query = deleteQueryConditions.toString();
        query = String.format(QUERY_DELETE_RESOURCES, query.substring(0, query.length() - 1));
        GenericSQLRepository.executeUpdate(CommonSpringBridge.getInstance().getUbldbDataSource(), query, values);
    }


}
