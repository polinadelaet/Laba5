package app.controller.command.exception;

public class CommandExecutionException extends Exception {
    public CommandExecutionException() {
    }

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(Throwable cause) {
        super(cause);
    }
}
