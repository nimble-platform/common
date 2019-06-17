package eu.nimble.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Utility methods to log erroneous cases in REST services
 *
 * Created by suat on 16-May-18.
 */
public class HttpResponseUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);

    public static ResponseEntity createResponseEntityAndLog(String msg, HttpStatus httpStatus) {
        return createResponseEntityAndLog(msg, null, httpStatus, LogLevel.WARN);
    }

    public static ResponseEntity createResponseEntityAndLog(String msg, Exception e, HttpStatus httpStatus) {
        if (httpStatus == HttpStatus.BAD_REQUEST) {
            return createResponseEntityAndLog(msg, e, httpStatus, LogLevel.WARN);
        } else {
            return createResponseEntityAndLog(msg, e, httpStatus, LogLevel.ERROR);
        }
    }


    public static ResponseEntity createResponseEntityAndLog(String msg, Exception e, HttpStatus httpStatus, LogLevel logLevel) {
        log(msg, e, logLevel);
        return ResponseEntity.status(httpStatus).body(msg);
    }

    public static void writeMessageServletResponseAndLog(HttpServletResponse response, String msg, Exception e, HttpStatus httpStatus, LogLevel logLevel) {
        log(msg, e, logLevel);

        response.setStatus(httpStatus.value());
        try {
            response.getOutputStream().write(msg.getBytes());
        } catch (IOException e1) {
            logger.error("Failed to write the error message to the output stream", e);
        }
    }

    private static void log(String msg, Exception e, LogLevel logLevel) {
        if(logLevel == null || logLevel == LogLevel.INFO) {
            if(e != null) {
                logger.info(msg, e);
            } else {
                logger.info(msg);
            }
        } else if (logLevel == LogLevel.WARN) {
            if (e != null) {
                logger.warn(msg, e);
            } else {
                logger.warn(msg);
            }
        } else if (logLevel == LogLevel.ERROR) {
            if (e != null) {
                logger.error(msg, e);
            } else {
                logger.error(msg);
            }
        }
    }

    public static String baseUrl(HttpServletRequest request) {
        String baseUrl = request.getRequestURL().toString();
        if (!baseUrl.endsWith("/"))
            baseUrl += "/";
        return baseUrl;
    }
}
