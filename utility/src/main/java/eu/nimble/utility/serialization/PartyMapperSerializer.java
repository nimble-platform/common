package eu.nimble.utility.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by suat on 11-Dec-18.
 */
public class PartyMapperSerializer {
    private ObjectMapper objectMapper;
    private PartySerializer partySerializer;

    public PartyMapperSerializer(ObjectMapper objectMapper, PartySerializer partySerializer) {
        this.objectMapper = objectMapper;
        this.partySerializer = partySerializer;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public PartySerializer getPartySerializer() {
        return partySerializer;
    }
}
