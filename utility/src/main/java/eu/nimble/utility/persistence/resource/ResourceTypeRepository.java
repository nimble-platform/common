package eu.nimble.utility.persistence.resource;

import eu.nimble.service.model.ubl.commonaggregatecomponents.ResourceType;
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
@Transactional(transactionManager = "ubldbTransactionManager")
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long>{
    @Query(value = "SELECT r FROM ResourceType r WHERE r.catalogueRepository = :repository AND r.entityID IN(:ids)")
    List<ResourceType> getManagedResourceTypes(@Param("repository") String repository, @Param("ids") Set<Long> ids);

    @Modifying
    @Query(value = "DELETE FROM ResourceType r WHERE r.catalogueRepository = :repository AND r.entityID IN (:ids)")
    void deleteEntityIdsForObject(@Param("repository") String repository, @Param("ids") Set<Long> ids);
}
