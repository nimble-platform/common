package eu.nimble.utility.serialization.resource_monitoring;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by suat on 11-Dec-18.
 */
public class PartyMapperSerializer {
    private ObjectMapper objectMapper;
    private PartySerializerGetIds partySerializer;

    public PartyMapperSerializer(ObjectMapper objectMapper, PartySerializerGetIds partySerializer) {
        this.objectMapper = objectMapper;
        this.partySerializer = partySerializer;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public PartySerializerGetIds getPartySerializer() {
        return partySerializer;
    }
}
