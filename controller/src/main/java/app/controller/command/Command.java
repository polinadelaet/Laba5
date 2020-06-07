package controller.command;

import controller.AbstractController;
import controller.command.exception.CommandExecutionException;
import controller.command.exception.WrongServiceException;
import controller.command.wish.Wish;
import controller.components.serviceMediator.Service;
import controller.exception.ControllerException;
import org.apache.commons.configuration2.Configuration;
import response.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

public abstract class Command {
    private final Set<Wish> wishes;

    protected final String commandName;
    protected final Map<String, String> arguments;
    protected final Configuration configuration;

    //For executing other commands in this command
    private AbstractController abstractController;

    /**
     * Use for creating command via factories.
     */
    public Command(String commandName,
                   Map<String, String> arguments,
                   Configuration configuration) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.configuration = configuration;
        this.wishes = createWishes(this.getClass());
    }


    /**
     * Will be used by controller for injecting services.
     */
    public final Set<Wish> getWishes() {
        return wishes;
    }

    public final Response execute() throws CommandExecutionException {
        injectServices();

        return processExecution();
    }

    private void injectServices() throws CommandExecutionException {
        Class<? extends Command> clazz = this.getClass();
        Field[] thisClassFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<>(Arrays.asList(thisClassFields));

        addParentField(fields, clazz);

        for (Field field : fields) {
            if (isService(field)) {
                injectService(field);
            }
        }
    }

    private void addParentField(List<Field> fields, Class<?> clazz) {
        Class<?> parentClass = clazz.getSuperclass();
        if (parentClass.equals(Command.class)) {
            return;
        }

        fields.addAll(Arrays.asList(parentClass.getDeclaredFields()));
        addParentField(fields, parentClass);
    }

    private boolean isService(Field field) {
        Class<?>[] interfaces = field.getType().getInterfaces();

        return Arrays.asList(interfaces).contains(Service.class);
    }

    private void injectService(Field field) throws CommandExecutionException {
        Class<? extends Service> serviceClass = (Class<? extends Service>) field.getType();

        Wish wish = getWishForService(wishes, serviceClass);

        if (wish != null) {
            if (wish.getService() == null) {
                throw new CommandExecutionException("Get null service for required: " + serviceClass.getSimpleName());
            }

            field.setAccessible(true);
            try {
                field.set(this, wish.getService());
            } catch (IllegalAccessException e) {
                throw new CommandExecutionException(e);
            }
            field.setAccessible(false);

            return;
        }

        throw new CommandExecutionException("No wishes found for required: " + serviceClass.getName());
    }

    @Nullable
    private Wish getWishForService(Set<Wish> wishes, Class<? extends Service> serviceClass) {
        for (Wish wish : wishes) {
            if (wish.contains(serviceClass)) {
                return wish;
            }
        }

        return null;
    }

    private Set<Wish> createWishes(Class<?> clazz) {
        Set<Wish> set = new HashSet<>();

//        Class<? extends Command> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (isService(field)) {
                Wish wish = new Wish((Class<? extends Service>) field.getType());
                set.add(wish);
            }
        }

        Class<?> parentClazz = clazz.getSuperclass();

        if (parentClazz.equals(Command.class)) {
            return set;
        }

        set.addAll(createWishes(parentClazz));

        return set;
    }

    public final void setAbstractController(AbstractController abstractController) {
        this.abstractController = abstractController;
    }

    /**
     * Use this method if you want to execute other command.
     * @param otherCommandName - has to be registered in the controller.
     * @param arguments - arguments for command.
     * @return - response or null if controller cannot execute this command.
     * @throws CommandExecutionException - in case of any exceptions.
     */
    @Nullable
    protected Response executeOtherCommand(@Nonnull String otherCommandName,
                                           @Nonnull Map<String, String> arguments) throws CommandExecutionException {
        try {
            return abstractController.handle(otherCommandName, arguments);
        } catch (ControllerException e) {
            throw new CommandExecutionException(e);
        }
    }

    /**
     * Here will be main logic of the command.
     * Use wishes to get services which this command depends on.
     *
     * @throws WrongServiceException - throw this exception if and only if you cannot find
     * required service in the wishes.
     *
     * @throws CommandExecutionException - in case of any other problems.
     */
    protected abstract Response processExecution() throws CommandExecutionException;
}
