package eu.nimble.utility;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.serialization.PartyMapperSerializer;
import eu.nimble.utility.serialization.PartySerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by suat on 24-Apr-18.
 */
public class JsonSerializationUtility {
    private static Logger logger = LoggerFactory.getLogger(JsonSerializationUtility.class);

    public static <T> T deserializeContent(String serializedContent, TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            T deserializedObject = mapper.readValue(serializedContent, typeReference);
            return deserializedObject;
        } catch (IOException e) {
            logger.error("Failed to deserialize the string: {}", serializedContent, e);
            throw e;
        }
    }

    public static <T> T deserializeContent(InputStream serializedContent, TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            T deserializedObject = mapper.readValue(serializedContent, typeReference);
            return deserializedObject;
        } catch (IOException e) {
            logger.error("Failed to deserialize the stream: ", e);
            throw e;
        }
    }

    public static <T> String serializeEntitySilently(T entity) {
        String serializedEntity = "";
        try {
            serializedEntity = getObjectMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            logger.warn("Failed to deserialize entity");
        }
        return serializedEntity;
    }

    public static ObjectMapper getMapperForTransientFields() {
        return new ObjectMapper().configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, false);
    }

    public static void removeHjidFields(JSONObject jsonObject) {

        if (jsonObject.has("hjid")) {
            jsonObject.remove("hjid");
        }
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                removeHjidFields((JSONObject) value);

            } else if (value instanceof JSONArray) {
                removeHjidFields((JSONArray) value);
            }
        }
    }

    public static void removeHjidFields(JSONArray jsonArray) {
        if(jsonArray.length() > 0) {
            if (jsonArray.get(0) instanceof JSONObject) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    removeHjidFields((JSONObject) jsonArray.get(i));
                }
            } else if (jsonArray.get(0) instanceof JSONArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    removeHjidFields((JSONArray) jsonArray.get(i));
                }
            }
        }
    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> Set<Long> extractAllHjids(T object) {
        JsonNode jsonObject = getJsonNodeFromObject(object);
        Set<Long> hjids = new HashSet<>();
        extractAllHjids(jsonObject, hjids);
        return hjids;
    }

    public static <T> Set<Long> extractAllHjidsExcludingPartyRelatedOnes(T object) {
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

    public static void extractAllHjids(JsonNode jsonObject, Set<Long> hjids) {
        if (jsonObject.has("hjid")) {
            Long hjid = jsonObject.get("hjid").asLong();
            if(hjid != null && hjid != 0) {
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
                    while(childrenNodes.hasNext()) {
                        extractAllHjids(childrenNodes.next(), hjids);
                    }
                }
            }
        }
    }

    public static <T> JsonNode getJsonNodeFromObject(T object) {
        ObjectMapper objectMapper = getObjectMapper();
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

    private static PartyMapperSerializer getPartyMapperSerializer() {
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
        PartySerializer partySerializer = new PartySerializer();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(PartyType.class, partySerializer);
        objectMapper.registerModule(simpleModule);
        return new PartyMapperSerializer(objectMapper, partySerializer);
    }
}
