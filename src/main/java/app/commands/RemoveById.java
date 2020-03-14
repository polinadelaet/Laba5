package app.commands;

import app.collection.WorkerCollection;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import app.response.Response;
import app.response.Status;

import java.util.Iterator;
import java.util.List;

public class RemoveById extends WorkerCollectionCommand {
    public RemoveById(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        try {
            Iterator<String> iterator = inputArguments.iterator();
            Integer id = Integer.parseInt(iterator.next());
            workerCollection.remove(new Worker(id));
            return new Response(Status.OK, "Продукт успешно удален из коллекции.");
        }catch (WorkerCollectionException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }
    }
}
