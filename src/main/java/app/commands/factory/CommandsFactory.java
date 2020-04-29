package app.commands.factory;

import app.collection.WorkerCollection;
import app.commands.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CommandsFactory {
    private final Map<String, Class<? extends WorkerCollectionCommand>> workerCollectionCommandsMap
            = new HashMap<String, Class<? extends WorkerCollectionCommand>>(){{
        put("add", Add.class);
        put("add_if_max", AddIfMax.class);
        put("clear", CLear.class);
        put("count_by_end_date", CountByEndDate.class);
        put("filter_by_person", FilterByPerson.class);
        put("insert_at", InsertAtIndex.class);
        put("print_field_descending_end_date", PrintFieldDescendingEndDate.class);
        put("remove_by_id", RemoveById.class);
        put("remove_lower", RemoveLower.class);
        put("show", Show.class);
        put("update", UpdateId.class);
        put("info", Info.class);
        put("save", Save.class);
    }};

    private final Map<String, Class<? extends ExecuteScript>> executeScriptCommandsMap
            = new HashMap<String, Class<? extends ExecuteScript>>() {{
        put("execute_script", ExecuteScript.class);
    }};

    private final Map<String, Class<? extends Command>> simpleCommandsMap
            = new HashMap<String, Class<? extends Command>>() {{
        put("exit", Exit.class);
        put("help", Help.class);
    }};

    private final WorkerCollection workerCollection;
    private final Set<Integer> scriptsHashCodes;

    public CommandsFactory(WorkerCollection workerCollection, Set<Integer> scriptsHashCodes) {
        this.workerCollection = workerCollection;
        this.scriptsHashCodes = scriptsHashCodes;
    }

    public Command create(String commandName, List<String> inputArguments) throws CommandCreationException {
        if (workerCollectionCommandsMap.containsKey(commandName)) {
            return createWorkerCollectionCommand(workerCollectionCommandsMap.get(commandName), inputArguments);
        }

        if (executeScriptCommandsMap.containsKey(commandName)) {
            return createExecuteScriptCommand(executeScriptCommandsMap.get(commandName), inputArguments);
        }

        return createSimpleCommand(simpleCommandsMap.get(commandName), inputArguments);
    }

    private WorkerCollectionCommand createWorkerCollectionCommand(Class<? extends WorkerCollectionCommand> commandClass,
                                                                  List<String> inputArguments) throws CommandCreationException {
        try {
            Constructor<? extends WorkerCollectionCommand> constructor = commandClass.getConstructor(List.class, WorkerCollection.class);
            return constructor.newInstance(inputArguments, workerCollection);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CommandCreationException();
        }
    }

    private ExecuteScript createExecuteScriptCommand(Class<? extends ExecuteScript> commandClass,
                                                     List<String> inputArguments) throws CommandCreationException {
        try {
            Constructor<? extends ExecuteScript> constructor = commandClass.getConstructor(List.class, WorkerCollection.class, Set.class);
            return constructor.newInstance(inputArguments, workerCollection, scriptsHashCodes);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CommandCreationException();
        }
    }

    private Command createSimpleCommand(Class<? extends Command> commandClass,
                                        List<String> inputArguments) throws CommandCreationException {
        try {
            Constructor<? extends Command> constructor = commandClass.getConstructor(List.class);
            return constructor.newInstance(inputArguments);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
            throw new CommandCreationException();
        }
    }
}
