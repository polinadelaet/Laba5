package app.connection;

public class ConnectionException extends Exception{

    public ConnectionException() {
    }

    public ConnectionException(String message){
        super(message);
    }
}
