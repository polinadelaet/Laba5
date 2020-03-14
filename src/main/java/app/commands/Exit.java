package app.commands;

import app.collection.WorkerCollection;
import app.response.Response;
import app.response.Status;

import java.util.List;

public class Exit extends WorkerCollectionCommand {
    public Exit(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        return new Response(Status.TIME_TO_EXIT, "Программа завершена.");
    }
}
