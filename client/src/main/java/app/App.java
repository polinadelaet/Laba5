package app;

import connection.Connection;
import connection.SocketConnection;
import connection.exception.ConnectionException;
import connectionWorker.ConnectionWorker;
import app.console.ConsoleWork;

public final class App {

    public static void main(String[] args) {
        Connection connection;
        try {
            connection = new SocketConnection("localhost", 52511, 128);
        } catch (ConnectionException e) {
            System.out.println("Все плохо.");
            return;
        }

        ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, ConnectionWorker.createDefault(connection), false);
        consoleWork.start();
    }
}
