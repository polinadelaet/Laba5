package app.collection.worker.workerCollectionException;

public final class WorkerCollectionException extends Exception {
    public WorkerCollectionException(String message) {
        super(message);
    }

    public WorkerCollectionException(Throwable cause) {
        super(cause);
    }
}
