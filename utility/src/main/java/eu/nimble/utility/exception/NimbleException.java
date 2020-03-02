package eu.nimble.utility.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NimbleException extends RuntimeException{

    private List<String> messageCodes;
    private List<List<String>> parameters = Arrays.asList(new ArrayList<>());;
    private Exception exception = null;
    private boolean writeToResponseOutputStream = false;

    public NimbleException(String messageCode) {
        this.messageCodes = Arrays.asList(messageCode);
    }

    public NimbleException(String messageCode, boolean writeToResponseOutputStream) {
        this.messageCodes = Arrays.asList(messageCode);
        this.writeToResponseOutputStream = writeToResponseOutputStream;
    }

    public NimbleException(String messageCode, Exception exception) {
        this.messageCodes = Arrays.asList(messageCode);
        this.exception = exception;
    }

    public NimbleException(String messageCode, List<String> parameters) {
        this.messageCodes = Arrays.asList(messageCode);
        this.parameters = Arrays.asList(parameters);
    }

    public NimbleException(String messageCode, List<String> parameters, boolean writeToResponseOutputStream) {
        this.messageCodes = Arrays.asList(messageCode);
        this.parameters = Arrays.asList(parameters);
        this.writeToResponseOutputStream = writeToResponseOutputStream;
    }

    public NimbleException(String messageCode, List<String> parameters, Exception exception) {
        this.messageCodes = Arrays.asList(messageCode);
        this.parameters = Arrays.asList(parameters);
        this.exception = exception;
    }

    public NimbleException(String messageCode, Exception exception, boolean writeToResponseOutputStream) {
        this.messageCodes = Arrays.asList(messageCode);
        this.exception = exception;
        this.writeToResponseOutputStream = writeToResponseOutputStream;
    }

    public NimbleException(String messageCode, List<String> parameters, Exception exception, boolean writeToResponseOutputStream) {
        this.messageCodes = Arrays.asList(messageCode);
        this.parameters = Arrays.asList(parameters);
        this.exception = exception;
        this.writeToResponseOutputStream = writeToResponseOutputStream;
    }

    public NimbleException(List<String> messageCodes, List<List<String>> parameters) {
        this.messageCodes = messageCodes;
        this.parameters = parameters;
    }

    public List<String> getMessageCodes() {
        return messageCodes;
    }

    public void setMessageCodes(List<String> messageCodes) {
        this.messageCodes = messageCodes;
    }

    public List<List<String>> getParameters() {
        return parameters;
    }

    public void setParameters(List<List<String>> parameters) {
        this.parameters = parameters;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean isWriteToResponseOutputStream() {
        return writeToResponseOutputStream;
    }

    public void setWriteToResponseOutputStream(boolean writeToResponseOutputStream) {
        this.writeToResponseOutputStream = writeToResponseOutputStream;
    }
}
