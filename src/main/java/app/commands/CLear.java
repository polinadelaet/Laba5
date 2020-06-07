package app.commands;

import app.collection.WorkerCollection;
import response.Response;
import response.Status;

import java.util.HashMap;
import java.util.List;

public final class CLear extends WorkerCollectionCommand {
    public CLear(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        workerCollection.removeAll();
        return new Response(Status.OK, "Коллекция очищена.");
    }
}
