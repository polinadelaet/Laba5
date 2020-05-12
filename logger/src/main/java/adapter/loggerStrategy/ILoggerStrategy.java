package adapter.loggerStrategy;

public interface ILoggerStrategy {
    void logError(String message);
    void logFatal(String message);
    void logInfo(String message);
    void logDebug(String message);
    void logWarn(String message);
}
