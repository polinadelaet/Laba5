package app.commands;

import app.collection.WorkerCollection;
import app.collection.worker.savingException.SavingException;
import app.response.Response;
import app.response.Status;

import java.io.File;
import java.util.List;
public final class Save extends WorkerCollectionCommand {
    public Save(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        try {
            String value = System.getenv("ver");
            workerCollection.save(new File(value));
        }catch (SavingException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }

        return null;
    }
}
