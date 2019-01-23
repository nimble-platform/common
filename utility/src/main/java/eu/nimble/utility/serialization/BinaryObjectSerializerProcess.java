package eu.nimble.utility.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.CommonSpringBridge;
import eu.nimble.utility.persistence.binary.ImageScaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Copies the binary content inside the passed {@link BinaryObjectType} to the binary content database.
 * Thumbnails are created for images and kept inside the passed {@link BinaryObjectType}. Non-image binary content is
 * nullified. The content created in the binary content database is referred via the {@code uri} property of the passed
 * {@link BinaryObjectType}
 */
public class BinaryObjectSerializerProcess extends JsonSerializer<BinaryObjectType> {

    @Override
    public void serialize(BinaryObjectType binaryObjectType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // if binary object has a uri (saved to binary content database), then delete the binary object
        if (binaryObjectType.getUri() == null || binaryObjectType.getUri().equals("")) {

            String mimeCode = binaryObjectType.getMimeCode();

            // create BinaryObjectType object
            BinaryObjectType binaryContentDbObject = new BinaryObjectType();
            binaryContentDbObject.setMimeCode(mimeCode);
            binaryContentDbObject.setFileName(binaryObjectType.getFileName());

            // check whether the binary object is an image or not
            if (mimeCode.contains("image/")) {
                // scale the image
                binaryContentDbObject.setValue(scaleImage(binaryObjectType.getValue(), false, mimeCode));
                binaryObjectType.setValue(scaleImage(binaryObjectType.getValue(), true, mimeCode));

            } else {
                binaryContentDbObject.setValue(binaryObjectType.getValue());
                binaryObjectType.setValue(null);
            }

            // save BinaryContent object
            binaryContentDbObject = CommonSpringBridge.getInstance().getBinaryContentService().createContent(binaryContentDbObject);
            binaryObjectType.setUri(binaryContentDbObject.getUri());
        }

        jsonGenerator.writeObject(null);
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
