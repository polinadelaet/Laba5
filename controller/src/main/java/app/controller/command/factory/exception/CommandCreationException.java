package app.controller.command.factory.exception;

public class CommandCreationException extends Exception {
    public CommandCreationException() {
    }

    public CommandCreationException(String message) {
        super(message);
    }

    public CommandCreationException(Throwable cause) {
        super(cause);
    }
}
