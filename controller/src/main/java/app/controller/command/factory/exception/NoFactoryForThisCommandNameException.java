package controller.command.factory.exception;

public class NoFactoryForThisCommandNameException extends CommandCreationException {
    public NoFactoryForThisCommandNameException() {
    }

    public NoFactoryForThisCommandNameException(String message) {
        super(message);
    }
}
