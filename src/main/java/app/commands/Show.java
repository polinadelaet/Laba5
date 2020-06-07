package app.commands;

import app.collection.WorkerCollection;
import response.Response;
import response.Status;

import java.util.HashMap;
import java.util.List;

public final class  Show extends WorkerCollectionCommand {

    public Show(HashMap<String, String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute(){
        String message = workerCollection.toString() + System.lineSeparator();
        return new Response(Status.OK, message);
    }

}
