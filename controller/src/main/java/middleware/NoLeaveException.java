package middleware;

public class NoLeaveException extends RuntimeException {
    public NoLeaveException(String message) {
        super(message);
    }
}
