package eu.nimble.utility.validation;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nimble.utility.JsonSerializationUtility;
import eu.nimble.utility.exception.AuthenticationException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by suat on 02-Sep-19.
 */
@Component
@Profile("!test")
public class ValidationUtil implements IValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    private static final String TOKEN_FIELD_REALM_ACCESS = "realm_access";
    private static final String TOKEN_FIELD_ROLES = "roles";

    public boolean validateRole(String token, NimbleRole[] requiredRoles) {
        try {
            String[] split_string = token.split("\\.");
            String base64EncodedBody = split_string[1];

            byte[] byteArray = Base64.decodeBase64(base64EncodedBody.getBytes());
            String decodedString = new String(byteArray);


            Base64 base64Url = new Base64(true);
            String body = new String(base64Url.decode(base64EncodedBody));

            ObjectMapper mapper = JsonSerializationUtility.getObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(body);
            JsonNode actualObj = mapper.readTree(parser);

            for (NimbleRole requiredRole : requiredRoles) {
                Iterator<JsonNode> availableRoles = actualObj.get(TOKEN_FIELD_REALM_ACCESS).get(TOKEN_FIELD_ROLES).elements();
                while (availableRoles.hasNext()) {
                    String role = availableRoles.next().asText();
                    if (role.contentEquals(requiredRole.getName())) {
                        return true;
                    }
                }
            }
            logger.warn("Token: {} does not include one of the roles: {}", token,
                    Arrays.asList(requiredRoles).stream().map(role -> role.getName()).collect(Collectors.joining(", ","[","]")));
            return false;

        } catch (IOException e) {
            logger.error("Failed to validate role from token: {}", token, e);
            return false;
        }
    }
}
