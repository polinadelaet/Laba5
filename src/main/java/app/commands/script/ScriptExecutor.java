package app.commands.script;

import app.collection.WorkerCollection;
import app.commands.factory.CommandsFactory;

public final class ScriptExecutor {

    private final WorkerCollection workerCollection;

    public ScriptExecutor(WorkerCollection workerCollection) {
        this.workerCollection = workerCollection;
    }

    public void execute(Script script) {
        try {
            script.getNextLine();
            CommandsFactory commandsFactory = new CommandsFactory(workerCollection);
            //commandsFactory.create()
        } catch (ScriptException e){
            e.getMessage();
        }
    }
}
