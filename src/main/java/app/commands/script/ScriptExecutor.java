package app.commands.script;

import app.collection.WorkerCollection;
import app.commands.factory.CommandCreationException;
import app.commands.factory.CommandsFactory;

import java.util.ArrayList;
import java.util.List;

public final class ScriptExecutor {

    private final WorkerCollection workerCollection;

    public ScriptExecutor(WorkerCollection workerCollection) {
        this.workerCollection = workerCollection;
    }

    public void execute(Script script) {
        try {
            while (true) {

                String line = script.getNextLine();
                CommandsFactory commandsFactory = new CommandsFactory(workerCollection);


                String [] subStrings = line.split(" +");
                List<String> arguments = new ArrayList<>();
                if (subStrings.length != 1){
                    arguments.add(subStrings[1]);
                }
                commandsFactory.create(subStrings[0],arguments);

                //String [] subStrings2 = script.getNextLine().split(" +");

            }
        } catch (ScriptException | CommandCreationException e){
            e.getMessage();
        }
    }
}
