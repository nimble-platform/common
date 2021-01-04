package eu.nimble.utility.serialization.binary_processing;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.persistence.binary.BinaryContentService;
import eu.nimble.utility.persistence.binary.ImageScaler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copies the binary content inside the passed {@link BinaryObjectType} to the binary content database.
 * Thumbnails are created for images and kept inside the passed {@link BinaryObjectType}. Non-image binary content is
 * nullified. The content created in the binary content database is referred via the {@code uri} property of the passed
 * {@link BinaryObjectType}
 */
public class BinaryObjectSerializerProcess extends JsonSerializer<BinaryObjectType> {

    // the names of the broken images
    private List<String> namesOfBrokenImages = new ArrayList<>();
    // binary objects to be saved
    private List<BinaryObjectType> binaryObjectsToBeSaved = new ArrayList<>();
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
                byte[] bytes = scaleImage(binaryObjectType.getValue(), false, mimeCode);
                // if bytes is equal to null,that means that we do not have a valid image
                if(bytes == null){
                    // since this is a broken image, add its name to the list
                    namesOfBrokenImages.add(binaryObjectType.getFileName());
                    jsonGenerator.writeObject(null);
                    return;
                }
                binaryContentDbObject.setValue(bytes);
                binaryObjectType.setValue(scaleImage(binaryObjectType.getValue(), true, mimeCode));

            } else {
                binaryContentDbObject.setValue(binaryObjectType.getValue());
                binaryObjectType.setValue(null);
            }

            // retrieve a uri for Binary Object
            String uri = new BinaryContentService().getURI();
            // set uris
            binaryObjectType.setUri(uri);
            binaryContentDbObject.setUri(uri);
            // add binary object to the list
            binaryObjectsToBeSaved.add(binaryContentDbObject);
        }

        jsonGenerator.writeObject(null);
    }

    private byte[] scaleImage(byte[] value, boolean isThumbnail, String mimeCode) throws IOException {
        // get correct format of the image (after image/... part)
        String format = mimeCode.substring(6);

        ByteArrayInputStream bais = new ByteArrayInputStream(value);
        BufferedImage image = null;
        try {
            image = new ImageScaler().scale(bais, isThumbnail);
        } finally{
            bais.close();
        }
        // if image is equal to null,that means that we do not have a valid image
        if(image == null){
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, format, baos);
        }finally {
            baos.close();
        }

        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }

    public List<String> getNamesOfBrokenImages() {
        return namesOfBrokenImages;
    }

    public List<BinaryObjectType> getBinaryObjectsToBeSaved() {
        return binaryObjectsToBeSaved;
    }
}
