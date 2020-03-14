package app.commands;

import app.collection.WorkerCollection;
import app.response.Response;

import java.util.List;
//TODO сделать эту хуету, тут сериализация
public class Save extends WorkerCollectionCommand {
    public Save(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        return null;
    }
}
