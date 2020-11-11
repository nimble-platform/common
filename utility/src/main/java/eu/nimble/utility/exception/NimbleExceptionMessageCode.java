package eu.nimble.utility.exception;

public enum NimbleExceptionMessageCode {
    GATEWAY_TIMEOUT_WAITING_FOR_ANOTHER_SERVER("GATEWAY_TIMEOUT.waitingForAnotherServer"),
    INTERNAL_SERVER_ERROR_NO_AVAILABLE_RESOURCE("INTERNAL_SERVER_ERROR.noAvailableResource"),
    INVALID_IMAGE("BAD_REQUEST.invalidImage")
    ;

    private String value;

    NimbleExceptionMessageCode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
