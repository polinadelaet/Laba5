package server;

import adapter.LoggerAdapter;
import connection.exception.ConnectionException;
import connectionWorker.ConnectionWorker;
import message.exception.WrongTypeException;
import response.Response;
import serializer.exception.DeserializationException;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public final class Server implements connectionManager.IRequestProcessor {
    protected final int port;
    protected final int connectionBufferSize;
    private final LoggerAdapter logger;

    public Server(int port, int connectionBufferSize, LoggerAdapter logger) {
        this.port = port;
        this.connectionBufferSize = connectionBufferSize;
        this.logger = LoggerAdapter.createDefault(Server.class.getSimpleName());
    }

    private void acceptNewClient(SelectionKey selectionKey) throws IOException{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            logger.info("Connection to server is successful.");

            connection.SocketChannelConnection socketChannelConnection = new connection.SocketChannelConnection(socketChannel, connectionBufferSize);

            connectionWorker.ConnectionWorker connectionWorker = ConnectionWorker.createDefault(socketChannelConnection);
            try {
                connectionWorker.read().getCommandQuery();
            } catch (ConnectionException | DeserializationException | WrongTypeException e){
                Response.createInternalError();
        }
    }
    }


    @Override
    public void process(ConnectionWorker connectionWorker) {

    }
}
