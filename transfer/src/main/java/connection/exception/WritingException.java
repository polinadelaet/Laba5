package connection.exception;

public class WritingException extends ConnectionException {
    public WritingException() {
    }

    public WritingException(String message) {
        super(message);
    }

    public WritingException(Throwable cause) {
        super(cause);
    }
}
