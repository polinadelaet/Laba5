package app.controller;

import adapter.LoggerAdapter;
import app.controller.command.Command;
import app.controller.command.exception.CommandExecutionException;
import app.controller.command.factory.CommandFactory;
import app.controller.command.factory.CommandMediator;
import app.controller.command.factory.exception.CommandCreationException;
import app.controller.components.serviceMediator.Service;
import app.controller.components.serviceMediator.ServiceMediator;
import app.controller.exception.ControllerException;
import middleware.Middleware;
import org.apache.commons.configuration2.Configuration;
import query.Query;
import response.Response;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * This is the Controller framework documentation.
 *
 * The Controller depends on:
 * <br></br>
 * compile group: 'com.kenai.nbpwr', name: 'javax-annotation', version: '1.3.7-201002241900'
 * <br></br>
 * compile group: 'org.apache.commons', name: 'commons-configuration2', version: '2.6'
 * <br></br>
 * compile project(':transfer_module')
 * <br></br>
 * compile project(":logger_adapter")
 * <br></br>
 *
 * Use Gradle for building dependencies. Transfer_module and logger_adapter are made by Mr.Kefir and Yorik.
 *
 * <br></br>
 * <br></br>
 *
 * <b>Controller</b>
 * <br></br>
 * Defines interface of Controller. Method {@link Controller#handle(Query) handle()} is only one that you
 * should use directly.
 *
 * <br></br>
 * <br></br>
 *
 * <b>AbstractController</b>
 * <br></br>
 * Honestly, it is a concrete class :) It is the heart of The Controller framework. Still you need to use
 * only {@link AbstractController#handle(Query) handle()} method. This method accept an instance of
 * transfer_module.Query that contains a commandName, a map of arguments and an accessToken. AccessToken
 * is not used in the framework. CommandName has to correspond with the commands map which you inject in the
 * {@link ControllerBuilder ControllerBuilder}. Arguments can be access in your {@link Command Command} impl.
 *
 * <br></br>
 * <br></br>
 *
 * <b>Command</b>
 * <br></br>
 * Via commands you can define useful logic to the Controller. Create a map commandName to commandClass and
 * inject it in {@link ControllerBuilder ControllerBuilder}. Often, you need to use some objects in your
 * command impls. For this purpose, the Wish Inject System has been created. You do not need to worry about
 * it's implementation. Just define a private, non final and non static field in your command impl. Type of
 * this field has to implements {@link Service Service marker interface}. It marks that an instance of
 * this class can be used in WIS. When your command impl is executed, WIS injects all services in their
 * fields defined in a command impl class.
 * <br></br>
 * <br></br>
 * Next. You can notice that you should overrides only Command.processExecution. When you are ready to
 * return some transfer_module.Response from your command impl use responseDTO protected field defined
 * in the {@link Command Command}. Creator of The Controller framework agrees that responseDTO sometimes
 * hard to use. Unfortunately, he has no time to improve it. So, you have to use existing responseDTO.
 * ResponseDTO contains two public field: a status and an answer. Status is a enum than encapsulates
 * standard http codes. By them you can understand on client what is a problem. For example, if you want to
 * send a bad request response:
 * <blockquote><pre>
 *      responseDTO.status = Status.BAD_REQUEST.getStringResult();
 *      responseDTO.answer = "Your data is bad.";
 *      return;
 *  </pre></blockquote>
 * <br></br>
 * <br></br>
 * Next. If you want to execute some other command registered in the Controller in your command impl then
 * use Command.executeOtherCommand(String commandName, Map(String, String) arguments). This method
 * returns a response. Please, be careful, there is no recursion detector so guarantee no recursion is upon to you.
 * <br></br>
 * <br></br>
 * Next. You can used apache configuration in your command impl. For this, please, read official documentation
 * on Apache software website. Framework only guarantees that every command has a protected field "configuration".
 * Also you can accessed a commandName protected field. It is value is the same that you defines in
 * {@link ControllerBuilder ControllerBuilder}.
 *
 * <br></br>
 * <br></br>
 *
 * <b>ControllerBuilder</b>
 * <br></br>
 * See a simple example that probably resolves all your problems.
 * <br></br>
 * First. Define a command map - string representation for each command.
 * <br></br>
 * Second. Define a service set - all services which can be injected in the commands by WIS.
 * <br></br>
 * Third. Create a app.controller.
 * <blockquote><pre>
 *       Map(String, Class(? extends Command)) commandMap = new HashMap();
 *         commandMap.put("1", EnterCommand.class);
 *         commandMap.put("2", EnterCommand.class);
 *         commandMap.put("3", EnterCommand.class);
 *
 *         Set(Service) services = new HashSet();
 *         services.add(exitingDirector);
 *
 *         Controller app.controller = new ControllerBuilder(configuration, commandMap)
 *                                             .buildServiceMediator(services)
 *                                             .build();
 *  </pre></blockquote>
 *
 * <br></br>
 * <br></br>
 *
 * And last words for advanced users. You can use defined {@link Middleware Middleware} class to create
 * a middleware tree based on pattern Composite and Chain of Responsibility. For this purpose,
 * {@link AbstractController AbstractController} extends {@link Middleware Middleware}.
 * <br></br>
 * <br></br>
 * Middleware System is a part of The Controller framework because in practice controllers and
 * middlewares are quite the same. For example, you can create a set of validators for queries and
 * only one app.controller. After that you can build the middleware tree which defines in other words "roads"
 * between each middleware. You can read more about this topic in the Google.
 * <br></br>
 * <br></br>
 * Use {@link Middleware#handle(Query)} when you want to delegate query to this middleware.
 * Use {@link Middleware#handle(Query, String)} in cases when executed middleware cannot
 * make a decision which leave should be executed next. Here String parameter is a leave name.
 * <br></br>
 * For example, your CommandNameMiddleware has two leaves: ControllerA and ControllerB. And this middleware
 * CAN resolve which app.controller is executed next. But
 * <br></br>
 * For example, your MiddlewareA has two leaves: MiddlewareB and MiddlewareC. Middlewares B and C have the
 * same leave - MiddlewareD. MiddlewareD has leaves: MiddlewareE and MiddlewareF. Typically, it means that
 * you have two possible lines of tree execution: A-B-D-E and A-C-D-F. But, unfortunately,  MiddlewareD cannot
 * resolve which of leaves it should execute. In this case, use
 * {@link Middleware#handle(Query, String)} in MiddlewareB and MiddlewareC, when you call MiddlewareD, and
 * defines which leave should be executed E or F.
 * <br></br>
 * <br></br>
 * Use {@link Middleware#addLeave(String, Middleware)} to configure this middlewares by pairs name - middleware.
 * @author Mr.Kefir
 */
public final class AbstractController extends Middleware implements Controller {
    private static final LoggerAdapter LOGGER_ADAPTER = LoggerAdapter.createDefault(AbstractController.class.getSimpleName());

    private final Configuration configuration;
    private final CommandFactory commandFactory;
    private final CommandMediator commandMediator;
    private ServiceMediator serviceMediator;


    public AbstractController(Configuration configuration,
                              CommandFactory commandFactory,
                              CommandMediator commandMediator) {
        this.configuration = configuration;
        this.commandFactory = commandFactory;
        this.commandMediator = commandMediator;
    }


    CommandMediator getCommandMediator() {
        return commandMediator;
    }

    ServiceMediator getServiceMediator() {
        return serviceMediator;
    }

    void setServiceMediator(ServiceMediator serviceMediator) {
        this.serviceMediator = serviceMediator;
    }

    /**
     * Use this method if you want to add a service.
     * If serviceMediator component is not included then this method do nothing.
     * NOTE: If you can use ControllerBuilder.buildServiceMediator() instead.
     */
    public void addService(@Nonnull Service service) {
        if (serviceMediator != null) {
            serviceMediator.add(service);
        }
    }

    /**
     * Use this method for handling a query.
     * Note! Query will be handled if and only if there query.name registered in the app.controller.
     * In other case handle method returns null.
     */
    public @Nullable Response handle(Query query) throws ControllerException {
        return handle(query.getCommandName(), query.getArguments());
    }

    /**
     * Do not use this method directly. It should be used only in app.controller module.
     */
    @Nullable
    public Response handle(String commandName,
                           Map<String, String> arguments) throws ControllerException {
        Class<? extends Command> commandClass = commandMediator.getCommandClass(commandName);
        if (commandClass == null) {
            //Not for this app.controller
            return null;
        }

        Command command;
        try {
            command = commandFactory.create(commandClass,
                                            commandName,
                                            arguments,
                                            configuration);
        } catch (CommandCreationException e) {
            LOGGER_ADAPTER.errorThrowable("Cannot create command", e);
            throw new ControllerException(e);
        }

        command.setAbstractController(this);

        if (serviceMediator != null) {
            command.getWishes().forEach(wish -> wish.visit(serviceMediator));
        }

        Response response;
        try {
            response = command.execute();
            LOGGER_ADAPTER.info("Command: \"" + commandName + "\" has been SUCCESSFULLY executed.");
        } catch (CommandExecutionException e) {
            LOGGER_ADAPTER.errorThrowable("Cannot execute command", e);
            throw new ControllerException(e);
        }

        return response;
    }
}
