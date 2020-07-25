package app.serverWork;

import app.connection.ConnectionException;
import app.connection.CreateConnectionException;
import app.console.ConsoleWork;
import app.controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import query.Query;
import response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    private final Controller controller;
    private final String host = "localhost";
    private final int port = 49998;
    private SocketChannel socketChannel;
    private ConsoleWork consoleWork;
    private ServerSocketChannel serverSocketChannel;
    private static final Logger log = LogManager.getLogger(Server.class);

    public Server(Controller controller) {
        this.controller = controller;
    }

    public void start () {
        consoleWork = new ConsoleWork(System.in, System.out, controller);
        new Thread(new Runnable() {
            @Override
            public void run() {
                consoleWork.start();
            }
        }).start();


        try {
            serverSocketChannel = ServerSocketChannel.open();
            log.info("ServerSocketChannel is opened.");
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            log.debug("The server was bind to host: " + host +
                        " and port: " + port);
        } catch (IOException e) {
            consoleWork.printLine("This port for Server is busy.");
        }

        while (true) {
            try {
                Query query = receiveQuery();
                log.debug("A query was receive from a client.");
                Response response = controller.handleQuery(query);
                log.debug("Response was send to a client.");
                sendResponse(response);
            } catch (ConnectionException e) {
                consoleWork.printLine(e.getMessage());
            }
        }
    }

    private SocketChannel createSocketChannel() throws CreateConnectionException {
        try {
            socketChannel = serverSocketChannel.accept();
            log.info("Connection established from..." + socketChannel.getRemoteAddress());
            return socketChannel;
        } catch (IOException e) {
            throw new CreateConnectionException("Failed to accept connection from client.");
        }
    }

    private Query receiveQuery() throws ConnectionException {
        try {
            socketChannel = createSocketChannel();
            ObjectInputStream objectInputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
            return (Query) objectInputStream.readObject();
        } catch (CreateConnectionException e) {
            throw new ConnectionException(e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new ConnectionException("Failed to get query from Client.");
        }
    }

    private void sendResponse(Response response) throws ConnectionException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            objectOutputStream.writeObject(response);
            socketChannel.close();
        } catch (IOException e) {
            throw new ConnectionException("Failed to transfer response to Client over the network.");
        }
    }
}
