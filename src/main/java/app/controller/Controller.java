package app.controller;

import adapter.LoggerAdapter;
import app.collection.WorkerCollection;
import app.commands.Command;
import app.commands.factory.CommandCreationException;
import app.commands.factory.CommandsFactory;
import query.Query;
import response.Response;
import response.Status;

import java.util.HashSet;
import java.util.Set;

public final class Controller {
    private final WorkerCollection workerCollection;
    private final LoggerAdapter logger;

    public Controller(WorkerCollection workerCollection) {
        this.workerCollection = workerCollection;
        logger = LoggerAdapter.createDefault(Controller.class.getSimpleName());
    }

    public Response handleQuery(Query query){
        try {
            Set<Integer> scriptsHashCodes = new HashSet<>();
            CommandsFactory commandsFactory = new CommandsFactory(workerCollection, scriptsHashCodes);
            Command command = commandsFactory.create(query.getCommandName(),query.getArguments());
            logger.info("Command is created.");
            return command.execute();
        } catch (CommandCreationException | NullPointerException e){
            return new Response(Status.INTERNAL_ERROR, e.getMessage());
        }
    }
}
