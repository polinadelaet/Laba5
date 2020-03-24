package app.controller;

import app.collection.WorkerCollection;
import app.commands.Command;
import app.commands.factory.CommandCreationException;
import app.commands.factory.CommandsFactory;
import app.query.Query;
import app.response.Response;
import app.response.Status;

public final class Controller {
    private final WorkerCollection workerCollection;
    private final CommandsFactory commandsFactory;

    public Controller(WorkerCollection workerCollection, CommandsFactory commandsFactory) {
        this.workerCollection = workerCollection;
        this.commandsFactory = commandsFactory;
    }

    public Response handleQuery(Query query){
        try {
            Command command = commandsFactory.create(query.getCommandName(),query.getArguments());
            Response response = command.execute();
            return command.execute();
        } catch (CommandCreationException | NullPointerException e){
            e.getMessage();
        }
        return new Response(Status.INTERNAL_SERVER_ERROR, "Ошибка сервера.");
    }
}
