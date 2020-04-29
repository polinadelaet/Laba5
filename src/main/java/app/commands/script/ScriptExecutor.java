package app.commands.script;

import app.collection.WorkerCollection;
import app.commands.Command;
import app.commands.factory.CommandCreationException;
import app.commands.factory.CommandsFactory;
import app.commands.script.argumentFormer.ArgumentFormer;
import app.commands.script.argumentFormer.CompositeArgumentFormer;
import app.commands.script.argumentFormer.SimpleArgumentFormer;
import app.commands.script.scriptException.RecursionException;
import app.commands.script.scriptException.ScriptException;

import java.util.*;

public final class ScriptExecutor {

    protected final Map<String, ArgumentFormer> choiceArgumentFormer = new HashMap<String, ArgumentFormer>(){{
        put("help", new SimpleArgumentFormer());
        put("info", new SimpleArgumentFormer());
        put("show", new SimpleArgumentFormer());
        put("add", new CompositeArgumentFormer());
        put("update", new CompositeArgumentFormer());
        put("remove_by_id", new SimpleArgumentFormer());
        put("clear", new SimpleArgumentFormer());
        put("save", new SimpleArgumentFormer());
        put("execute_script", new SimpleArgumentFormer());
        put("exit", new SimpleArgumentFormer());
        put("insert_at", new CompositeArgumentFormer());
        put("add_if_max", new CompositeArgumentFormer());
        put("remove_lower", new CompositeArgumentFormer());
        put("count_by_end_date", new SimpleArgumentFormer());
        put("filter_by_person", new CompositeArgumentFormer());
        put("print_field_descending_end_date", new SimpleArgumentFormer());
    }};

    private List<String> commandsName = new ArrayList<String>(){{
        add("help");
        add("info");
        add("show");
        add("add");
        add("update");
        add("remove_by_id");
        add("clear");
        add("save");
        add("execute_script");
        add("exit");
        add("insert_at");
        add("add_if_max");
        add("remove_lower");
        add("count_by_end_date");
        add("filter_by_person");
        add("print_field_descending_end_date");
    }};

    private final WorkerCollection workerCollection;
    private final Set<Integer> scriptsHashCodes;

    public ScriptExecutor(WorkerCollection workerCollection, Set<Integer> scriptsHashCodes) {
        this.workerCollection = workerCollection;
        this.scriptsHashCodes = scriptsHashCodes;
    }

    public String execute(Script script) throws ScriptException {
        if (!scriptsHashCodes.add(script.hashCode())){
            throw new RecursionException();
        }

        String message = "";
        while (script.hasNextLine()) {
            String firstLine = script.getNextLine();
            String[] subStrings = firstLine.split(" +");
            if (commandsName.contains(subStrings[0])) {

                ArgumentFormer argumentFormer = choiceArgumentFormer.get(subStrings[0]);
                CommandsFactory commandsFactory = new CommandsFactory(workerCollection, scriptsHashCodes);
                Command command;

                try {
                    command = commandsFactory.create(subStrings[0], argumentFormer.collectArguments(script));
                    message += command.execute().getMessage() + System.lineSeparator();
                } catch (RecursionException e) {
                    message += "Обнаруженно зацикливание" + System.lineSeparator();
                } catch (CommandCreationException e) {
                    throw new ScriptException(e);
                }
            } else {
                //System.out.println("ERROR: " + subStrings[0]);
                throw new ScriptException("Неправильный скрипт.");
            }
        }
        scriptsHashCodes.remove(script.hashCode());
        return message;
    }
}
