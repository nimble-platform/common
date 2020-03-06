package eu.nimble.utility.validation;

import eu.nimble.utility.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;
/**
 * Created by suat on 06-Sep-19.
 */
@Component
@Profile("test")
public class ValidationUtilMock implements IValidationUtil {
    private static final String TOKEN_FIELD_REALM_ACCESS = "realm_access";
    private static final String TOKEN_FIELD_ROLES = "roles";

    @Override
    public boolean validateRole(String token, List<String> userRoles, NimbleRole[] requiredRoles) {
        return true;
    }

    @Override
    public Claims validateToken(String token) throws AuthenticationException {
        // create Claims containing NimbleRole.COMPANY_ADMIN
        List<String> userRoles = Collections.singletonList(NimbleRole.COMPANY_ADMIN.getName());
        LinkedHashMap realmAccess = new LinkedHashMap();
        realmAccess.put(TOKEN_FIELD_ROLES,userRoles);

        Claims claims = new DefaultClaims();
        claims.put(TOKEN_FIELD_REALM_ACCESS,realmAccess);
        return claims;
    }
}
