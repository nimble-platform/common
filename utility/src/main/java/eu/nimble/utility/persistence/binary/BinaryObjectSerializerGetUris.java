package eu.nimble.utility.persistence.binary;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryObjectSerializerGetUris extends JsonSerializer<BinaryObjectType> {

    private List<String> listOfUris = new ArrayList<>();

    @Override
    public void serialize(BinaryObjectType binaryObjectType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (binaryObjectType.getUri() != null && !binaryObjectType.getUri().equals("")) {
            listOfUris.add(binaryObjectType.getUri());
        }

        jsonGenerator.writeObject(null);
    }

    public List<String> getListOfUris() {
        return listOfUris;
    }
}
