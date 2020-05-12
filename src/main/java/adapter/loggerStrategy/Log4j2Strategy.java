package adapter.loggerStrategy;


import adapter.formatter.IFormatter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class Log4j2Strategy implements ILoggerStrategy {
    private final Logger logger;
    private final IFormatter formatter;


    public Log4j2Strategy(String loggerName,
                          IFormatter formatter) {
        this.logger = LogManager.getLogger(loggerName);
        this.formatter = formatter;
    }


    @Override
    public void logError(String message) {
        logger.error("{}", ()->formatter.makeErrorColor(message));
    }

    @Override
    public void logFatal(String message) {
        logger.fatal("{}", ()->formatter.makeFatalColor(message));
    }

    @Override
    public void logInfo(String message) {
        logger.info("{}", ()->formatter.makeInfoColor(message));
    }

    @Override
    public void logDebug(String message) {
        logger.debug("{}", ()->formatter.makeDebugColor(message));
    }

    @Override
    public void logWarn(String message) {
        logger.warn("{}", ()->formatter.makeWarnColor(message));
    }
}
