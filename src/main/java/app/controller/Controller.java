package app.controller;

import app.collection.WorkerCollection;
import app.commands.Command;
import app.commands.factory.CommandCreationException;
import app.commands.factory.CommandsFactory;
import app.query.Query;
import app.response.Response;
import app.response.Status;

import java.util.HashSet;
import java.util.Set;

public final class Controller {
    private final WorkerCollection workerCollection;

    public Controller(WorkerCollection workerCollection) {
        this.workerCollection = workerCollection;
    }

    public Response handleQuery(Query query){
        try {
            Set<Integer> scriptsHashCodes = new HashSet<>();
            System.out.println("перед созданием команд фэктори");
            CommandsFactory commandsFactory = new CommandsFactory(workerCollection, scriptsHashCodes);
            System.out.println("перед созданием команды экзекьюта");

            Command command = commandsFactory.create(query.getCommandName(),query.getArguments());
            System.out.println("команда создалась но не исполнилась");
            return command.execute();
        } catch (CommandCreationException | NullPointerException e){
            return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
