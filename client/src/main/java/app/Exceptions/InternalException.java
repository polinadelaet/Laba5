package app.Exceptions;

/**
 * General error in internal operations
 */
public class InternalException extends Exception {
    public InternalException(String message){
        super(message);
    }
}
