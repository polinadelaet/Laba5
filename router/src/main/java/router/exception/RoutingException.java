package router.exception;

public class RoutingException extends RuntimeException {
    public RoutingException() {
    }

    public RoutingException(String message) {
        super(message);
    }

    public RoutingException(Throwable cause) {
        super(cause);
    }
}
