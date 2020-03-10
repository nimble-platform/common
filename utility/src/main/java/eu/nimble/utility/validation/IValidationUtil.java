package eu.nimble.utility.validation;

import eu.nimble.utility.exception.AuthenticationException;
import io.jsonwebtoken.Claims;

import java.util.List;

/**
 * Created by suat on 06-Sep-19.
 */
public interface IValidationUtil {
    /**
     * Checks whether the token include "one of the specified roles"
     */
    public boolean validateRole(String token,List<String> userRoles, NimbleRole[] requiredRoles);
    /**
     * Checks whether the token is valid or not
     */
    public Claims validateToken(String token) throws AuthenticationException;
}
