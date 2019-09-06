package eu.nimble.utility.validation;

/**
 * Created by suat on 06-Sep-19.
 */
public interface IValidationUtil {
    /**
     * Checks whether the token include "one of the specified roles"
     */
    public boolean validateRole(String token, NimbleRole[] requiredRoles);
}
