package connection;

import connection.clientConnectionException.ClientConnectionException;
import connection.clientConnectionException.CreateClientConnectionException;
import query.Query;
import response.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientConnection {

    private final String host = "localhost";
    private final int port = 49998;
    private SocketChannel socketChannel;

    private SocketChannel createChannel() throws CreateClientConnectionException {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host, port));
            return socketChannel;
        } catch (IOException e) {
            throw new CreateClientConnectionException("Failed to create a communication channel with the server.");
        }
    }


    public void writeQuery(Query query) throws ClientConnectionException {
        try {
            socketChannel = createChannel();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            objectOutputStream.writeObject(query);
        } catch (CreateClientConnectionException e) {
            throw new ClientConnectionException(e.getMessage());
        } catch (IOException e) {
            throw new ClientConnectionException("Failed to transfer data to Server over the network.");
        }
    }

    public Response receiveResponse() throws ClientConnectionException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
            Response response = (Response) objectInputStream.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            throw new ClientConnectionException("Failed to get data from Server.");
        }
    }
}
