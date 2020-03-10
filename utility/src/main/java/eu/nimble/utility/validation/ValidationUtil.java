package eu.nimble.utility.validation;

import eu.nimble.utility.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.jose4j.keys.RsaKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by suat on 02-Sep-19.
 */
@Component
@Profile("!test")
public class ValidationUtil implements IValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

    @Value("${nimble.keycloak.public-key}")
    private String keycloakPublicKey;

    public Claims validateToken(String token) throws AuthenticationException {
        try {
            RsaKeyUtil rsaKeyUtil = new RsaKeyUtil();
            PublicKey publicKey = rsaKeyUtil.fromPemEncoded(keycloakPublicKey);

            return (Claims) Jwts.parser().setSigningKey(publicKey).parse(token.replace("Bearer ", "")).getBody();
        } catch (Exception e){
            throw new AuthenticationException(String.format("Failed to check user authorization for token: %s", token), e);
        }
    }

    public boolean validateRole(String token,List<String> userRoles, NimbleRole[] requiredRoles) {
        for (NimbleRole requiredRole : requiredRoles) {
            for (String userRole : userRoles) {
                if (userRole.contentEquals(requiredRole.getName())) {
                    return true;
                }
            }
        }
        logger.warn("Token: {} does not include one of the roles: {}",token,
                Arrays.asList(requiredRoles).stream().map(role -> role.getName()).collect(Collectors.joining(", ","[","]")));
        return false;
    }
}
