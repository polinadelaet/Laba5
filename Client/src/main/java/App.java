import connection.ClientConnection;
import console.ConsoleWork;
import java.io.IOException;

public class App {

    private static final String PATH_TO_ID_GENERATOR = "./files/idGenerator";

    public static void main(String[] args) {

        ClientConnection clientConnection = new ClientConnection();
        ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, clientConnection);
        consoleWork.start();

    }
}
