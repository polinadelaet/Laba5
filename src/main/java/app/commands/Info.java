package app.commands;

import app.collection.WorkerCollection;
import app.response.Response;
import app.response.Status;

import java.util.List;

public final class Info extends WorkerCollectionCommand {


    public Info (List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute(){
        String message = "Тип коллекции: "+ workerCollection.getCollectionInfo().getCollectionType() + System.lineSeparator()
                + "Дата инициализации коллекции: " + workerCollection.getCollectionInfo().getCreationDate() + System.lineSeparator()
                + "Количество элементов коллекции: " + workerCollection.getCollectionInfo().getSize();
        return new Response(Status.OK, message);
    }
}
