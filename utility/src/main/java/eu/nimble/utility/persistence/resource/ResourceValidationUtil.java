package eu.nimble.utility.persistence.resource;

import eu.nimble.service.model.ubl.commonaggregatecomponents.ResourceType;
import eu.nimble.utility.CommonSpringBridge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static eu.nimble.utility.JsonSerializationUtility.extractAllHjids;
import static eu.nimble.utility.JsonSerializationUtility.extractAllHjidsExcludingPartyRelatedOnes;

/**
 * Created by suat on 07-Dec-18.
 */
public class ResourceValidationUtil {
    public static <T> void insertHjidsForObject(T object, String partyId, String catalogueRepository) {
        Set<Long> hjids = extractAllHjids(object);
        List<ResourceType> resources = new ArrayList<>();
        Set<Long> processedHjids = new HashSet<>();
        for (Long hjid : hjids) {
            if (processedHjids.contains(hjid)) {
                continue;
            }
            ResourceType resource = new ResourceType();
            resource.setEntityID(hjid);
            resource.setPartyID(partyId);
            resource.setCatalogueRepository(catalogueRepository);
            resources.add(resource);
            processedHjids.add(hjid);
        }
        CommonSpringBridge.getInstance().getResourceTypeRepository().save(resources);
    }

    public static <T> void removeHjidsForObject(T object, String catalogueRepository) {
        Set<Long> hjids = extractAllHjids(object);
        CommonSpringBridge.getInstance().getResourceTypeRepository().deleteEntityIdsForObject(catalogueRepository, hjids);
    }

    public static <T> boolean hjidsBelongsToParty(T object, String partyId, String catalogueRepository) {
        // assuming that we are injecting correct identifiers for the party instances
        Set<Long> hjids = extractAllHjidsExcludingPartyRelatedOnes(object);
        List<ResourceType> resources = CommonSpringBridge.getInstance().getResourceTypeRepository().getManagedResourceTypes(catalogueRepository, hjids);

        // check distinct parties
        Set<String> partyIds = new HashSet<>();
        Set<Long> managedIds = new HashSet<>();
        for (ResourceType resource : resources) {
            partyIds.add(resource.getPartyID());
            managedIds.add(resource.getEntityID());
        }
        if (partyIds.size() != 1
                || (partyIds.size() == 1 && !partyIds.toArray(new String[1])[0].contentEquals(partyId))) {
            return false;
        }

        // check non-managed ids
        if (hjids.size() > managedIds.size()) {
            return false;
        }

        return true;
    }

    public static <T> boolean hjidsExit(T object) {
        Set<Long> hjids = extractAllHjids(object);
        if (hjids.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
