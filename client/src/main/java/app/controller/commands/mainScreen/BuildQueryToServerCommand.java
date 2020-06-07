package controller.commands.mainScreen;

import adapter.LoggerAdapter;
import app.Console;
import app.Exceptions.InputException;
import app.Interpretator;
import app.Validator;
import app.Viewer;
import app.controller.services.connectionService.ConnectionService;
import app.query.CommandName;
import app.query.CommandType;
import app.query.queryBuilder.QueryBuilder;
import app.query.queryBuilder.QueryBuilderFactory;
import controller.command.Command;
import controller.command.exception.CommandExecutionException;
import org.apache.commons.configuration2.Configuration;
import query.Query;
import response.Response;
import response.Status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BuildQueryToServerCommand extends Command {
    private static final LoggerAdapter LOG_MANAGER = LoggerAdapter.createDefault(BuildQueryToServerCommand.class.getSimpleName());


    private QueryBuilderFactory queryBuilderFactory;
    private Validator validator;
    private Interpretator interpretator;
    private ConnectionService connectionService;
    private Console console;
    private Viewer viewer;

    /**
     * Use for creating command via factories.
     */
    public BuildQueryToServerCommand(String commandName,
                                     Map<String, String> arguments,
                                     Configuration configuration) {
        super(commandName, arguments, configuration);
    }

    @Override
    protected Response processExecution() throws CommandExecutionException {
        String userInput = arguments.get("userInput");

        String[] subStrings = getSubStrings(userInput);

        try {
            validator.validateCommandName(subStrings[0]);
        } catch (InputException e) {
            LOG_MANAGER.error("An error occurred entering the command...");
            return new Response(Status.BAD_REQUEST, "No such command");
        }

        CommandName commandName = CommandName.getCommandNameEnum(subStrings[0]);
        CommandType commandType = interpretator.interpretateCommandType(commandName);

        List<String> commandList = Arrays.asList(subStrings);
        try {
            validator.validateNumberOfArguments(commandName, commandList);
        } catch (InputException e) {
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }

        Map<String, String> arguments = new HashMap<>();
        if (commandType.equals(CommandType.COMPOUND_COMMAND)) {
            arguments = getArgumentsOfCompoundCommands(commandName);
        }
        arguments.put("login", this.arguments.get("login"));
        arguments.put("password", this.arguments.get("password"));

        QueryBuilder queryBuilder = queryBuilderFactory.getQueryBuilder(commandType);
        Query query;
        try {
            query = queryBuilder.buildQuery(commandName,
                    commandList,
                    arguments);
        } catch (InputException e) {
            LOG_MANAGER.errorThrowable(e);
            return new Response(Status.BAD_REQUEST, e.getMessage());
        }

        return connectionService.send(query);
    }

    private String[] getSubStrings(String userInput) {
        return userInput.split(" +");
    }

    /**
     * This method gets map of fields and invitation messages for user's input, display the message,
     * reads user's input and validate each field's value until user's input is correct.
     * Returns the map of field names and argument values.
     *
     * @param name
     * @return
     */
    private Map<String, String> getArgumentsOfCompoundCommands(CommandName name) {
        Map<String, String> mapOfArguments = new HashMap<>();
        Map<String, String> mapForInputArguments = interpretator.getMapForInputArguments(name, viewer);

        for (Map.Entry<String, String> entry : mapForInputArguments.entrySet()) {
            String field = entry.getKey();
            String message = entry.getValue();

            console.write(message);

            boolean isActive = true;
            String userInput;

            while (isActive) {
                try {
                    userInput = console.readLine();

                    if (userInput == null) {
                        LOG_MANAGER.warn("Поле со значением null.");
                        console.writeLine("Entered null. Please, repeat");
                        continue;
                    }

                    userInput = userInput.trim();

                    LOG_MANAGER.info("Введено значение поля.");

                    validator.validateElementFields(field, userInput);

                    mapOfArguments.put(field, userInput);
                    break;
                } catch (InputException e) {
                    LOG_MANAGER.errorThrowable("Введено некоректное значение.", e);
                    console.writeLine(e.getMessage());
                }
            }
        }
        return mapOfArguments;
    }
}
