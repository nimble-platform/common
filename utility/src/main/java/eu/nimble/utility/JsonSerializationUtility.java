package eu.nimble.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by suat on 24-Apr-18.
 */
public class JsonSerializationUtility {
    private static Logger logger = LoggerFactory.getLogger(JsonSerializationUtility.class);

    public static <T> T deserializeContent(InputStream serializedContent, TypeReference<T> typeReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            T deserializedObject = mapper.readValue(serializedContent, typeReference);
            return deserializedObject;
        } catch (IOException e) {
            logger.error("Failed to deserialize the stream: ", e);
            throw e;
        }
    }
}
