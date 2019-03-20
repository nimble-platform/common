package eu.nimble.utility.exception;

/**
 * Exception indicating an invalid authentication of a client in the scope of NIMBLE.
 *
 * Created by suat on 20-Mar-19.
 */
public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
