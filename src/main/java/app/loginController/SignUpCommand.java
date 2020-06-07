package app.loginController;

import app.dao.DAO;
import app.dao.DAOException;
import app.dao.GetQuery;
import app.dao.GetWithField;
import app.services.PasswordHashService;
import app.user.User;
import app.controller.command.Command;
import app.controller.command.exception.CommandExecutionException;
import org.apache.commons.configuration2.Configuration;
import response.Response;
import response.Status;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public final class SignUpCommand extends Command {
    private DAO<User> userDAO;
    private PasswordHashService passwordHashService;

    /**
     * Use for creating command via factories.
     */
    public SignUpCommand(String commandName, Map<String, String> arguments, Configuration configuration) {
        super(commandName, arguments, configuration);
    }

    @Override
    protected Response processExecution() throws CommandExecutionException {
        String login = arguments.get("login");
        String password = arguments.get("password");

        try {
            password = passwordHashService.hash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new CommandExecutionException(e);
        }

        GetQuery<User> getQuery = new GetWithField<>(User.class, "login", login);
        List<User> users;
        try {
            users = userDAO.get(getQuery);
        } catch (DAOException e) {
            throw new CommandExecutionException(e);
        }

        if (!users.isEmpty()) {
            return new Response(Status.BAD_REQUEST, "User with such login is already exist");
        }

        User user = new User(login, password);
        try {
            userDAO.create(user);
            return new Response(Status.OK, login + " " + password);
        } catch (DAOException e) {
            throw new CommandExecutionException(e);
        }
    }
}
