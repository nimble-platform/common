package eu.nimble.utility.persistence.resource;

import com.fasterxml.jackson.databind.JsonNode;
import eu.nimble.utility.JsonSerializationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by suat on 07-Dec-18.
 */
@Component
public class ResourceValidationUtility {

    private static Logger logger = LoggerFactory.getLogger(ResourceValidationUtility.class);

    @Autowired
    private Environment environment;

    public <T> boolean hjidsExit(T object) {
        Set<Long> hjids = extractAllHjids(object);
        if (hjids.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Extracts all the hjids from the given {@code object}
     *
     * @param object
     * @param <T>
     * @return
     */
    public <T> Set<Long> extractAllHjids(T object) {
        JsonNode jsonObject = JsonSerializationUtility.getObjectMapper().valueToTree(object);
        Set<Long> hjids = new HashSet<>();
        extractAllHjids(jsonObject, hjids);
        return hjids;
    }

    private void extractAllHjids(JsonNode jsonObject, Set<Long> hjids) {
        if (jsonObject.has("hjid")) {
            Long hjid = jsonObject.get("hjid").asLong();
            if (hjid != null && hjid != 0) {
                hjids.add(hjid);
            }
        }
        Iterator<String> keys = jsonObject.fieldNames();
        while (keys.hasNext()) {
            JsonNode child = jsonObject.get(keys.next());
            if (!child.isNull()) {
                if (child.isObject()) {
                    extractAllHjids(child, hjids);
                } else if (child.isArray()) {
                    Iterator<JsonNode> childrenNodes = child.elements();
                    while (childrenNodes.hasNext()) {
                        extractAllHjids(childrenNodes.next(), hjids);
                    }
                }
            }
        }
    }
}
