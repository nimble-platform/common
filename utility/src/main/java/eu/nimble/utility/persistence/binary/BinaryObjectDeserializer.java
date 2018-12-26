package eu.nimble.utility.persistence.binary;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Deserializes {@link BinaryObjectType}s by copying the passed binary information to the binary content database.
 * Thumbnails are created for images and kept inside the passed {@link BinaryObjectType}. Non-image binary content is
 * nullified. The content created in the binary content database is referred via the {@code uri} property of the passed
 * {@link BinaryObjectType}
 */
@Component
public class BinaryObjectDeserializer extends JsonDeserializer<BinaryObjectType> {

    @Autowired
    private BinaryContentService binaryContentService;

    @Override
    public BinaryObjectType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        if (node == null) {
            return null;
        }

        ObjectNode objectNode = (ObjectNode) node;

        // check whether we need to save BinaryContent object or not
        String uri = objectNode.get("uri").asText();

        if (uri == null || uri.equals("")) {

            byte[] value = objectNode.get("value").binaryValue();
            String mimeCode = objectNode.get("mimeCode").asText();
            String fileName = objectNode.get("fileName").asText();

            // create BinaryObjectType object
            BinaryObjectType binaryObject = new BinaryObjectType();
            binaryObject.setMimeCode(mimeCode);
            binaryObject.setFileName(fileName);

            // check whether the binary object is an image or not
            if (mimeCode.contains("image/")) {
                // scale the image
                binaryObject.setValue(scaleImage(value, false, mimeCode));
                objectNode = objectNode.put("value", scaleImage(value, true, mimeCode));
            } else {
                binaryObject.setValue(value);
                // remove 'value' field since we will store the content in binary content database
                objectNode.remove("value");
            }

            // save BinaryContent object
            binaryObject = this.binaryContentService.createContent(binaryObject);


            // set the uri
            objectNode = objectNode.put("uri", binaryObject.getUri());
        }

        String serializedBinaryObject = objectNode.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper = objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(serializedBinaryObject, BinaryObjectType.class);
    }


    private byte[] scaleImage(byte[] value, boolean isThumbnail, String mimeCode) throws IOException {
        // get correct format of the image (after image/... part)
        String format = mimeCode.substring(6);

        BufferedImage image = new ImageScaler().scale(new ByteArrayInputStream(value), isThumbnail);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
}
