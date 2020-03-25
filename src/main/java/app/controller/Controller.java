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
            CommandsFactory commandsFactory = new CommandsFactory(workerCollection, scriptsHashCodes);
            Command command = commandsFactory.create(query.getCommandName(),query.getArguments());
            return command.execute();
        } catch (CommandCreationException | NullPointerException e){
            return new Response(Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    //TODO: 1) Создать по запросу команду через фабрику. Раз ты не валидируешь запросы к серверу, то проверок здесь не нужно.
    //TODO: 2) Выполнить команду и получить ее ответ.
    //TODO: 3) Вернуть ответ из метода
}
