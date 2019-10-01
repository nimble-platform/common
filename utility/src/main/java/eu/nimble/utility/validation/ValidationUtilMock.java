package eu.nimble.utility.validation;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by suat on 06-Sep-19.
 */
@Component
@Profile("test")
public class ValidationUtilMock implements IValidationUtil {
    @Override
    public boolean validateRole(String token, NimbleRole[] requiredRoles) {
        return true;
    }
}
