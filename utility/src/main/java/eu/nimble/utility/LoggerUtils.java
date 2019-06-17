package eu.nimble.utility;

import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Created by dileepa on 5-June-19.
 */
public class LoggerUtils {

    public enum LogLevel {
        INFO, ERROR, DEBUG, WARN
    }

    /**
     * add contextual information to the log with MDC
     *
     * @param logger
     * @param params
     * @param logLevel
     * @param message
     */
    public static void logWithMDC(Logger logger, Map<String, String> params, LogLevel logLevel, String message) {

        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                MDC.put(key, params.get(key));
            }
        }
        switch (logLevel) {
            case INFO:
                logger.info(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            default:
                logger.info(message);
        }
        MDC.clear();
    }

    /**
     * add contextual information to the log with MDC
     *
     * @param logger
     * @param params
     * @param logLevel
     * @param message
     */
    public static void logWithMDC(Logger logger, Map<String, String> params, LogLevel logLevel, String message, Object... objects) {

        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                MDC.put(key, params.get(key));
            }
        }
        switch (logLevel) {
            case INFO:
                logger.info(message, objects);
                break;
            case ERROR:
                logger.error(message, objects);
                break;
            case DEBUG:
                logger.debug(message, objects);
                break;
            case WARN:
                logger.warn(message, objects);
                break;
            default:
                logger.info(message, objects);
        }
        MDC.clear();
    }

    /**
     * log error with contextual information with MDC
     *
     * @param logger
     * @param params
     * @param message
     * @param ex
     */
    public static void logErrorWithMDC(Logger logger, Map<String, String> params, String message, Throwable ex) {

        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                MDC.put(key, params.get(key));
            }
        }
        logger.error(message, ex);
        MDC.clear();
    }

}
