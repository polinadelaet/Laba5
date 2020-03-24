package app.commands;

import app.collection.WorkerCollection;
import app.commands.script.*;
import app.response.Response;
import app.response.Status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ExecuteScript extends WorkerCollectionCommand {

    private final Set<Integer> scriptsHashCodes;

    public ExecuteScript(List<String> inputArguments, WorkerCollection workerCollection, Set<Integer> scriptsHashCodes) {
        super(inputArguments, workerCollection);
        this.scriptsHashCodes = scriptsHashCodes;
    }

    @Override
    public Response execute() {
        ScriptReader scriptReader = new ScriptReader();
        try {
            ScriptExecutor scriptExecutor = new ScriptExecutor(workerCollection, scriptsHashCodes);
            
            return new Response(Status.OK, scriptExecutor.execute(scriptReader.read(inputArguments.get(1))));
        } catch (FileCreationException | ScriptException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }
    }
}
