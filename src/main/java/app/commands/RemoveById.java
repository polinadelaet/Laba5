package app.commands;

import app.collection.WorkerCollection;
import app.collection.worker.Worker;
import app.collection.worker.workerCollectionException.WorkerCollectionException;
import response.Response;
import response.Status;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class RemoveById extends WorkerCollectionCommand {
    public RemoveById(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        try {

            Integer id = Integer.parseInt(inputArguments.get("id"));
            workerCollection.remove(new Worker(id));
            return new Response(Status.OK, "Продукт успешно удален из коллекции.");
        }catch (WorkerCollectionException | NumberFormatException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }
    }
}
