package app.controller.command.exception;

public class WrongServiceException extends CommandExecutionException {
    public WrongServiceException() {
    }

    public WrongServiceException(String message) {
        super(message);
    }
}
