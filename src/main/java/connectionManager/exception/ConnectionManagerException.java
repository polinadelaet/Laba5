package connectionManager.exception;

public class ConnectionManagerException extends Exception {
    public ConnectionManagerException() {
    }

    public ConnectionManagerException(String message) {
        super(message);
    }

    public ConnectionManagerException(Throwable cause) {
        super(cause);
    }
}
