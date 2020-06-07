package app.controller.commands;

import app.controller.command.Command;
import app.controller.services.exitingDirector.ExitingDirector;
import org.apache.commons.configuration2.Configuration;
import response.Response;
import response.Status;

import java.util.Map;

public final class ExitCommand extends Command {
    private ExitingDirector exitingDirector;

    /**
     * Use for creating command via factories.
     *
     */
    public ExitCommand(String commandName,
                       Map<String, String> arguments,
                       Configuration configuration) {
        super(commandName, arguments, configuration);
    }

    @Override
    protected Response processExecution() {
        exitingDirector.exit();
        return new Response(Status.OK, "");
    }
}
