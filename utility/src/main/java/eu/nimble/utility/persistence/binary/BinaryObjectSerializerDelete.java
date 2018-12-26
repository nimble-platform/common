package eu.nimble.utility.persistence.binary;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.CommonSpringBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Deletes content from binary content database. The content is specified by {@code uri} of the passed {@link BinaryObjectType}.
 */
public class BinaryObjectSerializerDelete extends JsonSerializer<BinaryObjectType> {

    @Override
    public void serialize(BinaryObjectType binaryObjectType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // if binary object has a uri (saved to binary content database), then delete the binary object
        if (binaryObjectType.getUri() != null && !binaryObjectType.getUri().equals("")) {
            CommonSpringBridge.getInstance().getBinaryContentService().deleteContent(binaryObjectType.getUri());
        }

        jsonGenerator.writeObject(null);
    }
}
