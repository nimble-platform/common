package eu.nimble.utility.exception;

public enum NimbleExceptionMessageCode {
    GATEWAY_TIMEOUT_WAITING_FOR_ANOTHER_SERVER("GATEWAY_TIMEOUT.waitingForAnotherServer"),
    INTERNAL_SERVER_ERROR_NO_AVAILABLE_RESOURCE("INTERNAL_SERVER_ERROR.noAvailableResource"),
    INVALID_IMAGE("BAD_REQUEST.invalidImage"),
    INVALID_PROCESS_IDS("BAD_REQUEST.invalidProcessIds"),
    BAD_REQUEST_MISSING_NEGOTIATION_PROCESS_FOR_ORDER("BAD_REQUEST.missingNegotiationProcessForOrder"),
    BAD_REQUEST_MISSING_NEGOTIATION_PROCESS_FOR_TRANSPORT_EXECUTION_PLAN("BAD_REQUEST.missingNegotiationProcessForTransportExecutionPlan"),
    BAD_REQUEST_MISSING_NEGOTIATION_ORDER_PROCESS_FOR_FULFILMENT("BAD_REQUEST.missingNegotiationOrderProcessForFulfilment")
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
