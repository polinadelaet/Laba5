package adapter.formatter;

public interface IFormatter {
    String getExceptionMessage(String simpleMessage, Throwable throwable);

    String getExceptionMessage(Throwable throwable);

    String getExceptionMessage(String simpleMessage);

    String makeFatalColor(String message);

    String makeErrorColor(String message);

    String makeWarnColor(String message);

    String makeInfoColor(String message);

    String makeDebugColor(String message);
}
