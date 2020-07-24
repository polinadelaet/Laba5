package connection;

import reer.Kyk;
import response.Response;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientConnection {

    private final String host = "localhost";
    private final int port = 49999;

    public void writeData(String string) {
        try {
            SocketChannel myClient = SocketChannel.open(new InetSocketAddress(host, port));
            ObjectOutputStream  oos = new ObjectOutputStream(myClient.socket().getOutputStream());
            oos.writeObject(string);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response readResponseFromSocket(SocketChannel socketChannel){
        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
        Response response = null;
        try {
            socketChannel.read(buffer);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            response = (Response) objectInputStream.readObject();
            buffer.clear();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("kdkjdk");
        }
        return response;
    }
}
