package eu.nimble.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
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
    public static <T> void processBinaryContents (T entity) {
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BinaryObjectType.class, new BinaryObjectSerializerProcess());
        objectMapper.registerModule(simpleModule);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            objectMapper.writeValue(baos, entity);
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
    public static void removeBinaryContentFromDatabase(List<String> uris) {
        if(uris.size() > 0){
            CommonSpringBridge.getInstance().getBinaryContentService().deleteContents(uris);
        }
    }
}
