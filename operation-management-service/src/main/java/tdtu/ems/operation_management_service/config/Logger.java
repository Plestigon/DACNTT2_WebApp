package tdtu.ems.operation_management_service.config;

import org.slf4j.LoggerFactory;

public class Logger<T> {
    private final Class<T> type;
    private final org.slf4j.Logger logger;

    public Logger(Class<T> type) {
        this.type = type;
        logger = LoggerFactory.getLogger(type);;
    }

    public void Info(String functionName, String message) {
        logger.info(String.format("<INFO> [%1$s] %2$s", functionName, message));
    }

    public void Error(String functionName, String message) {
        logger.error(String.format("<ERROR> [%1$s] %2$s", functionName, message));
    }

    public Class<T> getType() {
        return type;
    }
}
