package eu.nimble.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.nimble.service.model.ubl.commonaggregatecomponents.ClauseType;
import eu.nimble.utility.serialization.ClauseDeserializer;
import eu.nimble.utility.serialization.XMLGregorianCalendarSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

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

    public static <T> String serializeEntity(T entity) throws JsonProcessingException {
        String serializedEntity = "";
        try {
            serializedEntity = getObjectMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize entity. class: {}", entity.getClass());
            throw e;
        }
        return serializedEntity;
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
        ObjectMapper mapper = new ObjectMapper();
        // the following deserialization feature is added since there might be unknown
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // add the following mapper feature so that the transient fields are not considered during serialization and deserialization
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

        // add deserializer to be able deserialize derived ClauseType instances properly
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ClauseType.class, new ClauseDeserializer());
        mapper.registerModule(module);

        SimpleModule dateModule = new SimpleModule();
        dateModule.addSerializer(XMLGregorianCalendar.class,new XMLGregorianCalendarSerializer());
        mapper.registerModule(dateModule);
        return mapper;
    }

    public static ObjectMapper getObjectMapperForFilledFields() {
        ObjectMapper mapper = getObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }

    public static ObjectMapper getMapperForTransientFields() {
        return new ObjectMapper().configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, false);
    }
}
