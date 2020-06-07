package app.controller.commands.enterScreen;

import app.controller.command.Command;
import app.controller.command.exception.CommandExecutionException;
import app.controller.services.connectionService.ConnectionService;
import org.apache.commons.configuration2.Configuration;
import query.Query;
import response.Response;
import response.Status;

import java.util.HashMap;
import java.util.Map;

public final class SignUpCommand extends Command {
    private ConnectionService connectionService;


    /**
     * Use for creating command via factories.
     */
    public SignUpCommand(String commandName, Map<String, String> arguments, Configuration configuration) {
        super(commandName, arguments, configuration);
    }

    @Override
    protected Response processExecution() throws CommandExecutionException {
        String userInput = arguments.get("userInput");
        String[] subStrings = userInput.split(" +");

        if (subStrings.length != 3) {
            return new Response(Status.BAD_REQUEST, "SignUp command format: \"signUp LOGIN PASSWORD\"");
        }

        String login = subStrings[1];
        String password = subStrings[2];

        Map<String, String> queryArguments = new HashMap<>();
        queryArguments.put("login", login);
        queryArguments.put("password", password);

        Query query = new Query("signUp", queryArguments);

        return connectionService.send(query);
    }
}
