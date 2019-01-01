package eu.nimble.utility.persistence.resource;

import eu.nimble.service.model.ubl.commonaggregatecomponents.ResourceType;
import eu.nimble.utility.CommonSpringBridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by suat on 10-Dec-18.
 */
public class ResourceTypePersistenceUtil {

    private static final String QUERY_GET_BY_IDS = "SELECT r FROM ResourceType r  WHERE r.catalogueRepository = :repository AND r.entityID IN (:ids)";
    private static final String QUERY_DELETE_BY_IDS = "DELETE FROM ResourceType r WHERE r.catalogueRepository = :repository AND r.entityID IN (:ids)";

    public static List<ResourceType> getManagedResourceTypes(String repository, Set<Long> ids) {
        return CommonSpringBridge.getInstance().getGenericJPARepository().getEntities(QUERY_GET_BY_IDS, new String[]{"repository", "ids"}, new Object[]{repository, ids});
    }

    public static void deleteEntityIdsForObject(String repository, Set<Long> ids) {
        CommonSpringBridge.getInstance().getGenericJPARepository().executeUpdate(QUERY_DELETE_BY_IDS, new String[]{"repository", "ids"}, new Object[]{repository, ids});
    }
}
