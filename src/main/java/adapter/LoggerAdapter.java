package adapter;

import adapter.formatter.IFormatter;
import adapter.formatter.Log4j2Formatter;
import adapter.loggerStrategy.ILoggerStrategy;
import adapter.loggerStrategy.Log4j2Strategy;


/**
 * Use for all logging needs.
 * You can get an instance using one of public static create methods.
 * NOTE: there are no handmade loggers. It is only adapter for your convenience.
 */
public final class LoggerAdapter {
    private final ILoggerStrategy loggerStrategy;
    private final IFormatter formatter;


    private LoggerAdapter(ILoggerStrategy loggerStrategy,
                          IFormatter formatter) {
        this.loggerStrategy = loggerStrategy;
        this.formatter = formatter;
    }

    /**
     * Create a loggerAdapter with default strategy.
     * It is a log4j2Strategy.
     * @param loggerName - give a unique name for your little baby logger! ^_^
     */
    public static LoggerAdapter createDefault(String loggerName) {
        IFormatter formatter = new Log4j2Formatter();
        return new LoggerAdapter(new Log4j2Strategy(loggerName, formatter),
                                 formatter);
    }

    /**
     * Create a loggerAdapter with log4j2Strategy.
     * @param clazz - will be used in the log4j2Strategy for creating instance of log4j2Logger
     */
    public static LoggerAdapter createLog4j2LoggerAdapter(Class<?> clazz) {
        IFormatter formatter = new Log4j2Formatter();
        return new LoggerAdapter(new Log4j2Strategy(clazz.getName(), formatter),
                                 formatter);
    }


    public void errorThrowable(String simpleMessage, Throwable throwable) {
        loggerStrategy.logError(formatter.getExceptionMessage(simpleMessage, throwable));
    }

    public void errorThrowable(Throwable throwable) {
        loggerStrategy.logError(formatter.getExceptionMessage(throwable));
    }

    public void errorThrowable(String simpleMessage) {
        loggerStrategy.logError(formatter.getExceptionMessage(simpleMessage));
    }

    public void fatalThrowable(String simpleMessage, Throwable throwable) {
        loggerStrategy.logFatal(formatter.getExceptionMessage(simpleMessage, throwable));
    }

    public void fatalThrowable(Throwable throwable) {
        loggerStrategy.logFatal(formatter.getExceptionMessage(throwable));
    }

    public void fatalThrowable(String simpleMessage) {
        loggerStrategy.logFatal(formatter.getExceptionMessage(simpleMessage));
    }

    public void debug(String message) {
        loggerStrategy.logDebug(message);
    }

    public void info(String message) {
        loggerStrategy.logInfo(message);
    }

    public void warn(String message) {
        loggerStrategy.logWarn(message);
    }
    public void error(String message) {
        loggerStrategy.logError(message);
    }

    public void fatal(String message) {
        loggerStrategy.logFatal(message);
    }
}
