package eu.nimble.utility.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.CommonSpringBridge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates binary objects stored in the ubl database. When this serializer is attached to
 * an {@link com.fasterxml.jackson.databind.ObjectMapper} and an object including several {@link BinaryObjectType}s residing
 * in various locations in the object hierarchy is serialized with the serializer, all {@link BinaryObjectType}s which have a uri
 * starting with the binary content url are accumulated inside the {@code objects}.
 */
public class BinaryObjectSerializerGetBinaryObjects extends JsonSerializer<BinaryObjectType> {

    private List<BinaryObjectType> objects = new ArrayList<>();

    @Override
    public void serialize(BinaryObjectType binaryObjectType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String binaryContentUrl = CommonSpringBridge.getInstance().getCommonConfig().getBinaryContentUrl();
        if (binaryObjectType.getUri() != null && !binaryObjectType.getUri().equals("") && binaryObjectType.getUri().startsWith(binaryContentUrl)) {
            objects.add(binaryObjectType);
        }

        jsonGenerator.writeObject(null);
    }

    public List<BinaryObjectType> getObjects() {
        return objects;
    }
}
