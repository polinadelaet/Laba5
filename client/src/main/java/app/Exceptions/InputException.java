package app.Exceptions;

/**
 * Exception of invalid user's input.
 */
public class InputException extends Exception {
    public  InputException (String message){
        super(message);
    }
}
