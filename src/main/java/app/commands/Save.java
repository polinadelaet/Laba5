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
            String path = System.getenv("WC_FILE");
            workerCollection.save(new File(path));
        }catch (SavingException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }

        return null;
    }
}
