package eu.nimble.utility.exception;

import java.util.List;

public class BinaryContentException extends NimbleException {
    public BinaryContentException(String message, List<String> parameters) {
        super(message, parameters);
    }
}
