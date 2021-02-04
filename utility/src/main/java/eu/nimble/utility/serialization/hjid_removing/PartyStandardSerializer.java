package eu.nimble.utility.serialization.hjid_removing;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.utility.JsonSerializationUtility;

import java.io.IOException;

/**
 * Created by suat on 11-Dec-18.
 */
public class PartyStandardSerializer extends JsonSerializer<PartyType> {

    @Override
    public void serialize(PartyType partyType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeRawValue(JsonSerializationUtility.getObjectMapper().writeValueAsString(partyType));
    }
}
