package server;

import adapter.LoggerAdapter;
import app.controller.Controller;
import connection.Connection;
import connection.SocketConnection;
import connection.exception.ConnectionException;
import connectionWorker.ConnectionWorker;
import console.ConsoleWork;
import message.EntityType;
import message.Message;
import message.exception.WrongTypeException;
import query.Query;
import response.Response;
import response.Status;
import serializer.exception.DeserializationException;
import serializer.exception.SerializationException;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public final class Server {
    private final int port;
    private final int connectionBufferSize;
    private final LoggerAdapter logger;

    private final Controller controller;

    public Server(int port, int connectionBufferSize, Controller controller) {
        this.port = port;
        this.connectionBufferSize = connectionBufferSize;
        this.controller = controller;
        this.logger = LoggerAdapter.createDefault(Server.class.getSimpleName());
    }

    public void start() throws IOException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection;
                try {
                    connection = new SocketConnection("localhost", 8080, 128);
                } catch (ConnectionException e) {
                    System.out.println("Все плохо.");
                    return;
                }

                ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, ConnectionWorker.createDefault(connection), true);
                consoleWork.start();
            }
        }).start();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            logger.info("Connection to server is successful");

            connection.SocketChannelConnection socketChannelConnection = new connection.SocketChannelConnection(socketChannel, connectionBufferSize);

            connectionWorker.ConnectionWorker connectionWorker = ConnectionWorker.createDefault(socketChannelConnection);

            try {
                Query query = connectionWorker.read().getCommandQuery();

                logger.debug("Query created: " + query);

                Response response = controller.handleQuery(query);

                logger.debug("Response created: " + response);

                try {
                    connectionWorker.send(new Message(EntityType.RESPONSE, Response.dtoOf(response)));
                } catch (SerializationException e) {
                    logger.errorThrowable("Cannot send response", e);
                }
            } catch (ConnectionException | DeserializationException | WrongTypeException e){
                logger.errorThrowable("Cannot get query from client", e);
                Response internalError = new Response(Status.INTERNAL_ERROR, "");

                try {
                    connectionWorker.send(new Message(EntityType.RESPONSE, Response.dtoOf(internalError)));
                } catch (ConnectionException | SerializationException ex) {
                    logger.fatalThrowable("You simply cannot see it... Take a worm", ex);
                    System.exit(1);
                }
            }
        }
    }
}
