package controller.command.factory;

import controller.command.Command;
import controller.command.factory.exception.CommandCreationException;
import org.apache.commons.configuration2.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public final class CommandFactory {
    /**
     * Overrides this method for creating command. Here you only need to pass a map of arguments.
     * DO NOT inject here any services and other entities in the command.
     */
    public Command create(Class<? extends Command> commandClass,
                          String commandName,
                          Map<String, String> arguments,
                          Configuration configuration) throws CommandCreationException {
        try {
            Constructor<? extends Command> constructor = commandClass.getConstructor(String.class,
                                                                                     Map.class,
                                                                                     Configuration.class);
            return constructor.newInstance(commandName,
                                           arguments,
                                           configuration);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new CommandCreationException(e);
        }
    }
}
