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
 * Aggregates the uri of the binary contents stored in the binary content database. When this serializer is attached to
 * an {@link com.fasterxml.jackson.databind.ObjectMapper} and an object including several {@link BinaryObjectType}s residing
 * in various locations in the object hierarchy is serialized with the serializer, uris of the all {@link BinaryObjectType}s
 * are accumulated inside the {@code listOfUris}. Then, the list can be used e.g. when deleting all the {@link BinaryObjectType}s
 * associated with a {@link eu.nimble.service.model.ubl.catalogue.CatalogueType}.
 */
public class BinaryObjectSerializerGetUris extends JsonSerializer<BinaryObjectType> {

    private List<String> listOfUris = new ArrayList<>();

    @Override
    public void serialize(BinaryObjectType binaryObjectType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String binaryContentUrl = CommonSpringBridge.getInstance().getCommonConfig().getBinaryContentUrl();
        if (binaryObjectType.getUri() != null && !binaryObjectType.getUri().equals("") && binaryObjectType.getUri().startsWith(binaryContentUrl)) {
            listOfUris.add(binaryObjectType.getUri());
        }

        jsonGenerator.writeObject(null);
    }

    public List<String> getListOfUris() {
        return listOfUris;
    }
}
