package app.Exceptions;

public final class ConsoleException extends UserInterfaceException {
    public ConsoleException(String message) {
        super(message);
    }

    public ConsoleException(Throwable cause) {
        super(cause);
    }
}
