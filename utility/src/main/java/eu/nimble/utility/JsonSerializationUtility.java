package eu.nimble.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import eu.nimble.service.model.ubl.commonaggregatecomponents.ClauseType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.serialization.ClauseDeserializer;
import eu.nimble.utility.serialization.MixInIgnoreType;
import eu.nimble.utility.serialization.XMLGregorianCalendarSerializer;
import eu.nimble.utility.serialization.hjid_removing.JsonFieldFilter;
import eu.nimble.utility.serialization.hjid_removing.PartyStandardSerializer;
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
        ObjectMapper mapper = getObjectMapper();

        try {
            T deserializedObject = mapper.readValue(serializedContent, typeReference);
            return deserializedObject;
        } catch (IOException e) {
            logger.error("Failed to deserialize the string: {}", serializedContent, e);
            throw e;
        }
    }

    public static <T> T deserializeContent(InputStream serializedContent, TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = getObjectMapper();

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
            ObjectMapper mapper = getObjectMapperWithMixIn(BinaryObjectType.class,MixInIgnoreType.class);
            serializedEntity = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            logger.warn("Failed to serialize entity: {}", entity.getClass());
        }
        return serializedEntity;
    }

    public static <T> String serializeEntitySilentlyWithMixin(T entity, Class target, Class source) {
        String serializedEntity = "";
        try {
            ObjectMapper mapper = getObjectMapperWithMixIn(target, source);
            serializedEntity = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            logger.warn("Failed to serialize entity: {}", entity.getClass());
        }
        return serializedEntity;
    }

    public static ObjectMapper getObjectMapperWithIgnoreMixin() {
        return getObjectMapperWithMixIn(BinaryObjectType.class, MixInIgnoreType.class);
    }

    public static ObjectMapper getObjectMapperWithMixIn(Class target,Class source){
        ObjectMapper mapper = getObjectMapper();
        mapper.addMixIn(target, source);
        return mapper;
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

    public static <T> T removeHjidFields(T object) throws IOException {
        return removeHjidFields(object, false);
    }

    public static <T> T removeHjidFields(T object, boolean excludePartyObjects) throws IOException{
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();

        if(excludePartyObjects) {
            SimpleModule simpleModule = new SimpleModule();
            PartyStandardSerializer partySerializer = new PartyStandardSerializer();
            simpleModule.addSerializer(PartyType.class, partySerializer);
            objectMapper.registerModule(simpleModule);
        }

        objectMapper.addMixIn(Object.class, JsonFieldFilter.class);
        String[] ignorableFieldNames = {"hjid"};
        FilterProvider filters = new SimpleFilterProvider().addFilter(JsonFieldFilter.JSON_FILTER, SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));
        return objectMapper.readValue(objectMapper.writer(filters).writeValueAsString(object), (Class<T>) object.getClass());
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // the following deserialization feature is added since there might be unknown
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // add the following mapper feature so that the transient fields are not considered during serialization and deserialization
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

        // add the following mapper feature since camunda injects serializer configurations that serialize entity fields
        // as appear in the @XmlElement annotations. however neither front-end nor catalogue service have these configurations
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        // add hibernate5 module to exclude lazy loaded collections from serialization
        mapper.registerModule(new Hibernate5Module());

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
