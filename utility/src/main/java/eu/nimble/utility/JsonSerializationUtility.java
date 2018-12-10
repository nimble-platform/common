package eu.nimble.utility;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public static <T> List<Long> extractAllHjids(T object) {
        JsonNode jsonObject = getJsonNodeFromObject(object);
        List<Long> hjids = new ArrayList<>();
        extractAllHjids(jsonObject, hjids);
        return hjids;
    }

    public static void extractAllHjids(JsonNode jsonObject, List<Long> hjids) {
        if (jsonObject.has("hjid")) {
            Long hjid = jsonObject.get("hjid").asLong();
            if(hjid != null) {
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
}
