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
import eu.nimble.service.model.ubl.commonaggregatecomponents.MetadataType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.serialization.*;
import eu.nimble.utility.serialization.generic.DateSerializer;
import eu.nimble.utility.serialization.generic.XMLGregorianCalendarSerializer;
import eu.nimble.utility.serialization.hjid_removing.JsonFieldFilter;
import eu.nimble.utility.serialization.hjid_removing.PartyStandardSerializer;
import eu.nimble.utility.serialization.ubl.ClauseDeserializer;
import eu.nimble.utility.serialization.ubl.IgnoreMixin;
import eu.nimble.utility.serialization.ubl.MetadataTypeMixin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
            ObjectMapper mapper = getObjectMapperWithMixIn(BinaryObjectType.class, IgnoreMixin.class);
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
        return getObjectMapperWithMixIn(BinaryObjectType.class, IgnoreMixin.class);
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

        // add deserializer to be able deserialize derived ClauseType instances properly
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ClauseType.class, new ClauseDeserializer());
        mapper.registerModule(module);

        // serializers for dates
        SimpleModule dateModule = new SimpleModule();
        dateModule.addSerializer(XMLGregorianCalendar.class,new XMLGregorianCalendarSerializer());
        dateModule.addSerializer(Date.class, new DateSerializer());
        mapper.registerModule(dateModule);

        // serializer for entities to rename Hyperjaxb-generated attributes.
        // We chose this way as it was not possible to transient fields in combination with the Hibernate5 module
        mapper.addMixIn(MetadataType.class, MetadataTypeMixin.class);

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

    /**
     * Method to get an ObjectMapper instance in any desired configuration based on the {@link SerializerConfig} configs.
     *
     * @param configCode Aggregated config indicating the desired mapper configurations. For instance, to exclude binary object bytes and lazy collections from serialization,
     *                   configCode should be set to 5, which is the sum of {@link SerializerConfig#EXCLUDE_BINARY_BYTES} and {@link SerializerConfig#EXCLUDE_LAZY_COLLECTIONS}
     * @return
     */
    public static ObjectMapper getObjectMapper(int configCode) {
        ObjectMapper mapper = getObjectMapper();
        List<Integer> configs = SerializerConfig.fragmentConfig(configCode);
        for (int i=1; i<configs.size(); i++) {
            if (configs.get(i) == 1) {
                switch (i+1) {
                    case 1: {
                        mapper.addMixIn(BinaryObjectType.class, IgnoreMixin.class);
                        break;
                    }
                    case 2: {
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                        break;
                    }
                    case 3: { // if lazy loading is disabled, this config should not be set. Otherwise, lazy loaded collections are not included in the serialization.
                        mapper.registerModule(new Hibernate5Module());
                        break;
                    }
                }
            }
        }
        return mapper;
    }
}
