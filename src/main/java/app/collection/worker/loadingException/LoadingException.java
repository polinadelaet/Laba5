package app.collection.worker.loadingException;

public class LoadingException extends Exception{
    public LoadingException(String message) {
        super(message);
    }

    public LoadingException(Throwable cause) {
        super(cause);
    }
}
