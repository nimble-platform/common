package eu.nimble.utility.persistence;

import eu.nimble.service.model.ubl.commonaggregatecomponents.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by suat on 10-Dec-18.
 */
@Transactional(transactionManager = "ubldbTransactionManager")
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long>{
    @Query(value = "SELECT DISTINCT r.partyID FROM ResourceType r WHERE r.catalogueRepository = :repository AND r.entityID IN (:ids)")
    List<String> getDistinctPartyIds(@Param("repository") String repository, @Param("ids") List<Long> ids);

    @Modifying
    @Query(value = "DELETE FROM ResourceType r WHERE r.catalogueRepository = :repository AND r.entityID IN (:ids)")
    void deleteEntityIdsForObject(@Param("repository") String repository, @Param("ids") List<Long> ids);


}
