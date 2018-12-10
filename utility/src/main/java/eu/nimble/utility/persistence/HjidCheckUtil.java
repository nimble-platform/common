package eu.nimble.utility.persistence;

import com.fasterxml.jackson.databind.JsonNode;
import eu.nimble.service.model.ubl.commonaggregatecomponents.ResourceType;
import eu.nimble.utility.SpringBridge;

import java.util.ArrayList;
import java.util.List;

import static eu.nimble.utility.JsonSerializationUtility.extractAllHjids;
import static eu.nimble.utility.JsonSerializationUtility.getJsonNodeFromObject;

/**
 * Created by suat on 07-Dec-18.
 */
public class HjidCheckUtil {
    public static <T> void insertHjidsForObject(T object, String partyId) {
        List<Long> hjids = extractAllHjids(object);
        List<ResourceType> resources = new ArrayList<>();
        for(Long hjid : hjids) {
            ResourceType resource = new ResourceType();
            resource.setHjid(hjid);
            resource.setPartyID(partyId);
            resources.add(resource);
        }
        SpringBridge.getInstance().getResourceTypeRepository().save(resources);
    }

    public static <T> void removeHjidsForObject(T object) {
        List<Long> hjids = extractAllHjids(object);
        SpringBridge.getInstance().getResourceTypeRepository().deleteEntityIdsForObject(hjids);
    }

    public static <T> boolean hjidsBelongsToParty(T object, String partyId) {
        List<Long> hjids = extractAllHjids(object);
        List<String> partyIds = SpringBridge.getInstance().getResourceTypeRepository().getDistinctPartyIds(hjids);

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
