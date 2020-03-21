package app.commands;

import app.collection.WorkerCollection;
import app.commands.script.FileCreationException;
import app.commands.script.ScriptException;
import app.commands.script.ScriptExecutor;
import app.commands.script.ScriptReader;
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
            scriptReader.read(inputArguments.get(1));
            ScriptExecutor scriptExecutor = new ScriptExecutor(workerCollection);
        } catch (FileCreationException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }
        return null;
    }
}
