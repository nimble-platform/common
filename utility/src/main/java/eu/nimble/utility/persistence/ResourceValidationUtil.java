package eu.nimble.utility.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import eu.nimble.service.model.ubl.commonaggregatecomponents.ResourceType;
import eu.nimble.utility.CommonSpringBridge;

import java.util.ArrayList;
import java.util.List;

import static eu.nimble.utility.JsonSerializationUtility.extractAllHjids;
import static eu.nimble.utility.JsonSerializationUtility.getJsonNodeFromObject;

/**
 * Created by suat on 07-Dec-18.
 */
public class ResourceValidationUtil {
    public static <T> void insertHjidsForObject(T object, String partyId, String catalogueRepository) {
        List<Long> hjids = extractAllHjids(object);
        List<ResourceType> resources = new ArrayList<>();
        for(Long hjid : hjids) {
            ResourceType resource = new ResourceType();
            resource.setHjid(hjid);
            resource.setPartyID(partyId);
            resource.setCatalogueRepository(catalogueRepository);
            resources.add(resource);
        }
        CommonSpringBridge.getInstance().getResourceTypeRepository().save(resources);
    }

    public static <T> void removeHjidsForObject(T object, String catalogueRepository) {
        List<Long> hjids = extractAllHjids(object);
        CommonSpringBridge.getInstance().getResourceTypeRepository().deleteEntityIdsForObject(catalogueRepository, hjids);
    }

    public static <T> boolean hjidsBelongsToParty(T object, String partyId, String catalogueRepository) {
        List<Long> hjids = extractAllHjids(object);
        List<String> partyIds = CommonSpringBridge.getInstance().getResourceTypeRepository().getDistinctPartyIds(catalogueRepository, hjids);

        if(partyIds.size() > 1 ||
                (partyIds.size() == 1 && !partyIds.get(0).contentEquals(partyId))) {
            return false;
        } else {
            return true;
        }
    }

    public static <T> boolean hjidsExit(T object) {
        JsonNode jsonObject = getJsonNodeFromObject(object);

        List<Long> hjids = extractAllHjids(jsonObject);
        if(hjids.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
