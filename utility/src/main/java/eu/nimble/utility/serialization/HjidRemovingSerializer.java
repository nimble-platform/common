package eu.nimble.utility.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.nimble.service.model.ubl.commonaggregatecomponents.CatalogueLineType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.utility.JsonSerializationUtility;
import eu.nimble.utility.serialization.PartyMapperSerializer;
import eu.nimble.utility.serialization.PartySerializerGetIds;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by suat on 15-Oct-19.
 */
public class HjidRemovingSerializer extends JsonSerializer {

    private Set<Long> partyRelatedHjids;

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // first get the hjids included in the party objects
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
        PartySerializerGetIds partySerializer = new PartySerializerGetIds();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(PartyType.class, partySerializer);
        objectMapper.registerModule(simpleModule);
        objectMapper.writeValueAsString(object);
        partyRelatedHjids = partySerializer.getParsedIds();

        JsonNode objectJson = removeHjidFields(object);
        jsonGenerator.writeTree(objectJson);

    }

    public JsonNode removeHjidFields(Object object) {
        JsonNode jsonObject = JsonSerializationUtility.getObjectMapper().valueToTree(object);
        removeHjidFields(jsonObject);
        jsonObject.toString();
        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    private void removeHjidFields(JsonNode jsonObject) {
        if (jsonObject.has("hjid")) {
            Long hjidValue = jsonObject.get("hjid").longValue();
            if (!this.partyRelatedHjids.contains(hjidValue)) {
                if (jsonObject instanceof ObjectNode) {
                    ((ObjectNode) jsonObject).remove("hjid");
                }
            }
        }
        Iterator<String> keys = jsonObject.fieldNames();
        while (keys.hasNext()) {
            JsonNode child = jsonObject.get(keys.next());
            if (!child.isNull()) {
                if (child.isObject()) {
                    removeHjidFields(child);
                } else if (child.isArray()) {
                    Iterator<JsonNode> childrenNodes = child.elements();
                    while (childrenNodes.hasNext()) {
                        removeHjidFields(childrenNodes.next());
                    }
                }
            }
        }
    }
}