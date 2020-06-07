package app.Exceptions;

public class UserInterfaceException extends RuntimeException {
    public UserInterfaceException() {
    }

    public UserInterfaceException(String message) {
        super(message);
    }

    public UserInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInterfaceException(Throwable cause) {
        super(cause);
    }
}
