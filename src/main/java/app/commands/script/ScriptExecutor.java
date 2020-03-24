package app.commands.script;

import app.collection.WorkerCollection;
import app.commands.factory.CommandCreationException;
import app.commands.factory.CommandsFactory;
import app.commands.script.argumentFormer.ArgumentFormer;
import app.commands.script.argumentFormer.CompositeArgumentFormer;
import app.commands.script.argumentFormer.SimpleArgumentFormer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ScriptExecutor {

    //TODO: плохое название, не отображает сути
    protected final Map<String, ArgumentFormer> commands = new HashMap<String, ArgumentFormer>(){{
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
        put("filter_by_person", new SimpleArgumentFormer());
        put("print_field_descending_end_date", new SimpleArgumentFormer());
    }};

    //TODO: плохое название, добавь s
    private List<String> commandName = new ArrayList<String>(){{
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


    public ScriptExecutor(WorkerCollection workerCollection) {
        this.workerCollection = workerCollection;
    }

    //TODO: добавить защиту от рекурсивного вызова скриптов (в случае обнаружения рекурсии команда должна пропускаться)
    //TODO: 1) Для этого определить hashCode для Script.
    //TODO: 2) Создать сет хэш-кодов скриптов (должна быть одна на все команды executeScript для этого ее необходимо пробрасывать параметром через сперва конструктор executeScript, откуда пробрасывать в ScriptExecutor).
    //TODO: 3) В начале выполнения скрипта попытаться добавить хэш-код нового скрипта в коллекцию.
    //TODO: 4) Если хэш-код не добавился, то есть set.add(script) == false, прервать исполнение скрипта с ошибкой.
    //TODO: 5) После выполнения скрипта удалить хэш-код из сета.
    //TODO: 6) Так как работаешь с рекурсией, не забудь, что ошибка с сообщением о рекурсии может появиться в строке 81, в таком случае тебе нужно просто проигнорирвоать текущую команду и написать в результат, что обнаружено зацикливание.
    public String execute(Script script) {
        String message = "";
        try {
            while (script.hasNextLine()) {
                String firstLine = script.getNextLine();
                String[] subStrings = firstLine.split(" +");
                if (commandName.contains(subStrings[0])) {
                    ArgumentFormer argumentFormer = commands.get(subStrings[0]);
                    CommandsFactory commandsFactory = new CommandsFactory(workerCollection);
                    //TODO: нечитаемо: в столбик или переменные вынести
                    message += commandsFactory.create(subStrings[0], argumentFormer.collectArguments(script)).execute().toString() + System.lineSeparator();
                } //TODO: лишнее else
                else throw new ScriptException("Неправильный скрипт.");
            }
        } catch (ScriptException | CommandCreationException e){
            //TODO: это что?
            e.getMessage();
        }
        return message;
    }
}
