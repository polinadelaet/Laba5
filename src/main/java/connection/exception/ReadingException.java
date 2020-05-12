package connection.exception;

public class ReadingException extends ConnectionException {
    public ReadingException() {
    }

    public ReadingException(String message) {
        super(message);
    }

    public ReadingException(Throwable cause) {
        super(cause);
    }
}
