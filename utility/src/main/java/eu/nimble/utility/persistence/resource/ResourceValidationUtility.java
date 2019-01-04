package eu.nimble.utility.persistence.resource;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.utility.JsonSerializationUtility;
import eu.nimble.utility.serialization.PartyMapperSerializer;
import eu.nimble.utility.serialization.PartySerializerGetIds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * Created by suat on 07-Dec-18.
 */
@Component
public class ResourceValidationUtility {

    private static Logger logger = LoggerFactory.getLogger(ResourceValidationUtility.class);

    @Autowired(required = false)
    @Qualifier("ubldbDataSource")
    private DataSource ubldbDataSource;

    public <T> void insertHjidsForObject(T object, String partyId, String catalogueRepository) {
        Set<Long> hjids = extractAllHjidsExcludingPartyRelatedOnes(object);
        ResourcePersistenceUtility.insertResourcesForParty(catalogueRepository, partyId, hjids);
    }

    public <T> void removeHjidsForObject(T object, String catalogueRepository) {
        // assuming that we are injecting correct identifiers for the party instances
        Set<Long> hjids = extractAllHjidsExcludingPartyRelatedOnes(object);
        if (hjids.size() > 0) {
            ResourcePersistenceUtility.deleteResourcesForParty(catalogueRepository, hjids);
        }
    }

    public <T> boolean hjidsBelongsToParty(T object, String partyId, String catalogueRepository) {
        // assuming that we are injecting correct identifiers for the party instances
        Set<Long> hjids = extractAllHjidsExcludingPartyRelatedOnes(object);
        if (hjids.size() == 0) {
            return true;
        }
        Set<String> partyIds = new HashSet<>();
        Set<Long> managedIds = new HashSet<>();

        List<Resource> managedEntities = ResourcePersistenceUtility.getResourcesForIds(catalogueRepository, hjids);
        for(Resource res : managedEntities) {
            partyIds.add(res.getPartyId());
            managedIds.add(res.getEntityId());
        }

        // check distinct parties
        if (partyIds.size() != 1
                || (partyIds.size() == 1 && !partyIds.toArray(new String[1])[0].contentEquals(partyId))) {
            return false;
        }

        // check only the managed ids are provided in the update operation
        if (!(managedIds.containsAll(hjids) && hjids.containsAll(managedIds))) {
            return false;
        }

        return true;
    }

    public <T> boolean hjidsExit(T object) {
        Set<Long> hjids = extractAllHjids(object);
        if (hjids.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Extracts all the hjids from the given {@code object}
     *
     * @param object
     * @param <T>
     * @return
     */
    public <T> Set<Long> extractAllHjids(T object) {
        JsonNode jsonObject = getJsonNodeFromObject(object);
        Set<Long> hjids = new HashSet<>();
        extractAllHjids(jsonObject, hjids);
        return hjids;
    }

    /**
     * Extracts all the hjids included in the given {@code object} except the ones included in the
     * {@link PartyType} instances.
     *
     * @param object
     * @param <T>
     * @return
     */
    public <T> Set<Long> extractAllHjidsExcludingPartyRelatedOnes(T object) {
        Set<Long> hjids = extractAllHjids(object);
        Set<Long> partyHjids;
        PartyMapperSerializer partyMapperSerializer = getPartyMapperSerializer();

        try {
            partyMapperSerializer.getObjectMapper().writeValueAsString(object);
            partyHjids = partyMapperSerializer.getPartySerializer().getParsedIds();

        } catch (JsonProcessingException e) {
            String msg = String.format("Failed to extract hjids from object with class: %s", object.getClass());
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
        hjids.removeAll(partyHjids);
        return hjids;
    }

    private void extractAllHjids(JsonNode jsonObject, Set<Long> hjids) {
        if (jsonObject.has("hjid")) {
            Long hjid = jsonObject.get("hjid").asLong();
            if (hjid != null && hjid != 0) {
                hjids.add(hjid);
            }
        }
        Iterator<String> keys = jsonObject.fieldNames();
        while (keys.hasNext()) {
            JsonNode child = jsonObject.get(keys.next());
            if (!child.isNull()) {
                if (child.isObject()) {
                    extractAllHjids(child, hjids);
                } else if (child.isArray()) {
                    Iterator<JsonNode> childrenNodes = child.elements();
                    while (childrenNodes.hasNext()) {
                        extractAllHjids(childrenNodes.next(), hjids);
                    }
                }
            }
        }
    }

    private <T> JsonNode getJsonNodeFromObject(T object) {
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
        String serializedObject;
        try {
            serializedObject = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            String msg = String.format("Failed to serialize the object with class: %s", object.getClass());
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }

        JsonFactory factory = objectMapper.getFactory();
        JsonNode jsonObject;
        try {
            JsonParser parser = factory.createParser(serializedObject);
            jsonObject = objectMapper.readTree(parser);
            return jsonObject;

        } catch (IOException e) {
            String msg = String.format("Failed to deserialize the object with class: %s, string: %s", object.getClass(), serializedObject);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    private PartyMapperSerializer getPartyMapperSerializer() {
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
        PartySerializerGetIds partySerializer = new PartySerializerGetIds();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(PartyType.class, partySerializer);
        objectMapper.registerModule(simpleModule);
        return new PartyMapperSerializer(objectMapper, partySerializer);
    }
}
