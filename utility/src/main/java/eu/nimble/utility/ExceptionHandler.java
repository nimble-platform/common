package eu.nimble.utility;

import eu.nimble.utility.exception.NimbleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@RestControllerAdvice
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

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
            logger.error(logMessages.toString(),e.getException());
        }
        else {
            logger.error(logMessages.toString());
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
}