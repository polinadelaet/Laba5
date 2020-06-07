package controller.command.factory;

import controller.command.Command;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * This class determines command which are available for the controller.
 * Note! Query will be handled if and only if there are a factory for this query.name.
 */
public final class CommandMediator {
    private final Map<String, Class<? extends Command>> commands;


    public CommandMediator(@Nonnull Map<String, Class<? extends Command>> commands) {
        this.commands = commands;
    }


    @Nullable
    public Class<? extends Command> getCommandClass(String commandName) {
        if (commands.containsKey(commandName)) {
            return commands.get(commandName);
        }

        return null;
    }

    public void add(String commandName, Class<? extends Command> commandClazz) {
        commands.put(commandName, commandClazz);
    }
}
