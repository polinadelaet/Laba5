package app.collection.worker.savingException;

public final class SavingException extends Exception {
    public SavingException(String message) {
        super(message);
    }

    public SavingException(Throwable cause) {
        super(cause);
    }
}
