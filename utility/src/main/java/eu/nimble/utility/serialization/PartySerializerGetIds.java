package eu.nimble.utility.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.utility.JsonSerializationUtility;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Serializes a party object by also keeping track of the ids contained in the party object itself and the other objects
 * contained in the party.
 *
 * Created by suat on 11-Dec-18.
 */
public class PartySerializerGetIds extends JsonSerializer<PartyType> {

    private Set<Long> parsedIds = new HashSet<>();

    @Override
    public void serialize(PartyType partyType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        this.parsedIds.addAll(JsonSerializationUtility.extractAllHjids(partyType));
        jsonGenerator.writeString(JsonSerializationUtility.getObjectMapper().writeValueAsString(partyType));
    }

    public Set<Long> getParsedIds() {
        return this.parsedIds;
    }
}
