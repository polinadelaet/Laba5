package app.collection.getQuery;

import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;

import java.util.Collection;
import java.util.List;

//Паттерн Command
public abstract class GetQuery {
    public abstract List<Worker> execute(List<Worker> workers) throws WorkerCollectionException;
}
