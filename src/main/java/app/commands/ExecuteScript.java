package app.commands;

import app.collection.WorkerCollection;
import app.commands.script.FileCreationException;
import app.commands.script.ScriptExecutor;
import app.commands.script.ScriptReader;
import app.commands.script.scriptException.RecursionException;
import app.commands.script.scriptException.ScriptException;
import response.Response;
import response.Status;

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

        try {
            ScriptReader scriptReader = new ScriptReader();
            ScriptExecutor scriptExecutor = new ScriptExecutor(workerCollection, scriptsHashCodes);

            String message = scriptExecutor.execute(scriptReader.read(inputArguments.get(0)));
            return new Response(Status.OK, message);
        }catch (RecursionException e){
            return new Response(Status.BAD_REQUEST,"Рекурсия.");
        }
        catch (FileCreationException | ScriptException e){
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }
    }
}
