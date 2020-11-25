package eu.nimble.utility.serialization.binary_processing;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.CommonSpringBridge;

import java.io.IOException;

/**
 * Clear the uri of the passed {@link BinaryObjectType} if it has a uri starting with the binary content url.
 */
public class BinaryObjectSerializerClearUris extends JsonSerializer<BinaryObjectType> {


    @Override
    public void serialize(BinaryObjectType binaryObjectType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String binaryContentUrl = CommonSpringBridge.getInstance().getCommonConfig().getBinaryContentUrl();
        if (binaryObjectType.getUri() != null && !binaryObjectType.getUri().equals("") && binaryObjectType.getUri().startsWith(binaryContentUrl)) {
            binaryObjectType.setUri(null);
        }

        jsonGenerator.writeObject(null);
    }

}
