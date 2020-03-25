package app.commands;

import app.collection.WorkerCollection;
import app.response.Response;

import java.util.List;
public final class Save extends Command {
    public Save(List<String> inputArguments) {
        super(inputArguments);
    }

    @Override
    public Response execute() {
        //TODO: после того, как будут выполнены тудушки в Worker, IdGenerator и в WorkerCollection реализовать команду
        return null;
    }
}
