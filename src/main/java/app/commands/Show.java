package app.commands;

import app.collection.WorkerCollection;
import app.response.Response;
import app.response.Status;

import java.util.List;

public class Show extends WorkerCollectionCommand {

    public Show(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute(){
        String message = workerCollection.toString();
        return new Response(Status.OK, message);
    }

}
