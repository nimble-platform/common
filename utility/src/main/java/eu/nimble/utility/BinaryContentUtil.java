package eu.nimble.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.exception.BinaryContentException;
import eu.nimble.utility.persistence.binary.BinaryContentService;
import eu.nimble.utility.serialization.BinaryObjectSerializerProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class BinaryContentUtil {

    private static final Logger logger = LoggerFactory.getLogger(BinaryContentUtil.class);

    /**
     * Processes the {@link BinaryObjectType}s as a side effect using {@link BinaryObjectSerializerProcess}.
     * Images are replaced with thumbnails and other binary contents are nullified. Original content is stored in the
     * binary content database and referred via the {@code uri} field of {@link BinaryObjectType}s.
     *
     * @param entity
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> void processBinaryContents (T entity, BinaryContentService binaryContentService) {
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        BinaryObjectSerializerProcess binaryObjectSerializerProcess = new BinaryObjectSerializerProcess();
        simpleModule.addSerializer(BinaryObjectType.class,binaryObjectSerializerProcess);
        objectMapper.registerModule(simpleModule);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            objectMapper.writeValue(baos, entity);
            // check whether we have any broken images or not
            if(binaryObjectSerializerProcess.getNamesOfBrokenImages().size() > 0){
                throw new BinaryContentException("The following images are not valid : " + binaryObjectSerializerProcess.getNamesOfBrokenImages());
            }
            // save binary objects
            binaryContentService.createContents(binaryObjectSerializerProcess.getBinaryObjectsToBeSaved());
        } catch (IOException e) {
            String serializedEntity = JsonSerializationUtility.serializeEntitySilently(entity);
            logger.warn("Failed to serialize entity for processing the binary content: {}", serializedEntity);
            throw new RuntimeException("Failed to serialize entity for processing the binary content:",e);
        } finally {
            if(baos != null){
                try {
                    baos.close();
                } catch (IOException e) {
                    logger.warn("Failed to close output stream: ",e);
                }
            }
        }
    }

    /**
     * removes binary contents with the given ids from the database
     * @param uris
     */
    public static void removeBinaryContentFromDatabase(List<String> uris, BinaryContentService binaryContentService) {
        if(uris.size() > 0){
            binaryContentService.deleteContents(uris);
        }
    }
}
