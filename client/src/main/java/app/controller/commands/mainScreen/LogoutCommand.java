package app.controller.commands.mainScreen;

import app.controller.command.Command;
import org.apache.commons.configuration2.Configuration;
import response.Response;
import response.Status;

import java.util.Map;

public final class LogoutCommand extends Command {
    /**
     * Use for creating command via factories.
     */
    public LogoutCommand(String commandName, Map<String, String> arguments, Configuration configuration) {
        super(commandName, arguments, configuration);
    }

    @Override
    protected Response processExecution() {
        return new Response(Status.GO_BACK, "");
    }
}
