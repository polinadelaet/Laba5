package adapter.formatter;

import java.util.Arrays;

public final class Log4j2Formatter implements IFormatter {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";


    @Override
    public String getExceptionMessage(String simpleMessage, Throwable throwable) {
        String fullMessage = formFullThrowableMessage(throwable);
        return String.format("Got a throwable. Simple message: %1s.%2s%3s", simpleMessage, System.lineSeparator(), fullMessage);
    }

    @Override
    public String getExceptionMessage(Throwable throwable) {
        return "Got a throwable:" + System.lineSeparator() + formFullThrowableMessage(throwable);
    }

    @Override
    public String getExceptionMessage(String simpleMessage) {
        return "Got a throwable. Simple message: " + simpleMessage;
    }

    @Override
    public String makeFatalColor(String message) {
        return ANSI_RED + message + ANSI_RESET;
    }

    @Override
    public String makeErrorColor(String message) {
        return ANSI_PURPLE + message + ANSI_RESET;
    }

    @Override
    public String makeWarnColor(String message) {
        return ANSI_YELLOW + message + ANSI_RESET;
    }

    @Override
    public String makeInfoColor(String message) {
        return ANSI_WHITE + message + ANSI_RESET;
    }

    @Override
    public String makeDebugColor(String message) {
        return ANSI_BLUE +  message + ANSI_RESET;
    }


    private String formFullThrowableMessage(Throwable throwable) {
        return String.format("%1s: %2s" + System.lineSeparator() + "%3s",
                            throwable.getClass().getSimpleName(),
                            throwable.getMessage(),
                            Arrays.stream(throwable.getStackTrace())
                                  .map(StackTraceElement::toString)
                                  .reduce((a, b) -> a + System.lineSeparator() + "\t--- " + b)
                                  .get());
    }
}
