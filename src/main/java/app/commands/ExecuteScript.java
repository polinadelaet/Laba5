package app.commands;

import app.collection.WorkerCollection;
import app.commands.script.*;
import app.response.Response;
import app.response.Status;

import java.util.List;

public final class ExecuteScript extends WorkerCollectionCommand {

    public ExecuteScript(List<String> inputArguments, WorkerCollection workerCollection) {
        super(inputArguments, workerCollection);
    }

    @Override
    public Response execute() {
        ScriptReader scriptReader = new ScriptReader();
        try {
            ScriptExecutor scriptExecutor = new ScriptExecutor(workerCollection);
            return new Response(Status.OK, scriptExecutor.execute(scriptReader.read(inputArguments.get(1))));
        } catch (FileCreationException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }
    }
}
