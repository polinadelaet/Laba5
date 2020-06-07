package middleware;

public class MiddlewareException extends Exception {
    public MiddlewareException() {
    }

    public MiddlewareException(String message) {
        super(message);
    }

    public MiddlewareException(Throwable cause) {
        super(cause);
    }
}
