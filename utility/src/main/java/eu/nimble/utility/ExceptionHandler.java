package eu.nimble.utility;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import eu.nimble.utility.exception.NimbleException;
import eu.nimble.utility.exception.NimbleExceptionMessageCode;
import org.apache.tomcat.jdbc.pool.PoolExhaustedException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    private final String SQL_INSUFFICIENT_RESOURCES_EXCEPTION_CLASS="53";

    @Autowired
    private MessageSource messageSource;

    /**
     * This method will handle all {@link NimbleException}s
     * It will log the error message in English and create an {@link ResponseEntity} containing error message in the requested language {@link Locale}.
     * */
    @org.springframework.web.bind.annotation.ExceptionHandler({NimbleException.class})
    public ResponseEntity handle(NimbleException e, Locale locale, HttpServletResponse response) {

        // if the cause of this exception is also a NimbleException, then we should use it
        // since it is the real exception we need to handle
        if(e.getException() != null && e.getException() instanceof NimbleException){
            e = (NimbleException) e.getException();
        }

        // get the root cause of exception
        Throwable rootCause = e.getException() == null ? null: getRootCause(e.getException());
        // if the root cause of the exception is PoolExhaustedException, PSQLException (Insufficient resources class) or HystrixRuntimeException,
        // create a new NimbleException with the proper error messages
        if(rootCause instanceof PoolExhaustedException || (rootCause instanceof PSQLException && ((PSQLException) rootCause).getSQLState().startsWith(SQL_INSUFFICIENT_RESOURCES_EXCEPTION_CLASS))){
            e = new NimbleException(NimbleExceptionMessageCode.INTERNAL_SERVER_ERROR_NO_AVAILABLE_RESOURCE.toString(), (Exception) rootCause);
        }
        else if(rootCause instanceof HystrixRuntimeException){
            e = new NimbleException(NimbleExceptionMessageCode.GATEWAY_TIMEOUT_WAITING_FOR_ANOTHER_SERVER.toString(), (Exception) rootCause);
        }

        // get log (in English) and error (in the specified locale) messages
        StringBuilder logMessages = new StringBuilder();
        StringBuilder errorMessages = new StringBuilder();
        int messageCodeSize = e.getMessageCodes().size();
        for (int i = 0; i < messageCodeSize ; i++) {
            String logMessage = messageSource.getMessage(e.getMessageCodes().get(i), e.getParameters().get(i).toArray(),Locale.ENGLISH);
            String errorMessage = messageSource.getMessage(e.getMessageCodes().get(i), e.getParameters().get(i).toArray(),locale);
            logMessages.append(logMessage).append(System.lineSeparator());
            errorMessages.append(errorMessage).append(System.lineSeparator());
        }

        // HTTP status
        int httpStatus = HttpStatus.valueOf(e.getMessageCodes().get(0).substring(0,e.getMessageCodes().get(0).indexOf("."))).value();

        // log the exception
        if(e.getException() != null){
            logger.error(logMessages.toString(), e.getException());
        }
        else {
            logger.error(logMessages.toString(), e);
        }

        // write error message to HttpServletResponse Output Stream
        if(e.isWriteToResponseOutputStream()){
            response.setStatus(httpStatus);
            try{
                response.getOutputStream().write(errorMessages.toString().getBytes());
            }
            catch (Exception e1){
                logger.error("Failed to write error message to the output stream",e1);
            }
            return null;
        }

        return ResponseEntity.status(httpStatus).body(errorMessages.toString());
    }

    private Throwable getRootCause(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}