package app.connection;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ConnectionWorker {

    private void receiveClientData(SelectionKey key) throws ConnectionException {
        try {
            if (key.isAcceptable()){
                SocketChannel client = server.accept();
                System.out.println("Accepted connection from " + client);
                client.configureBlocking(false);
                client.register(selector,SelectionKey.OP_READ);
                continue; // in the start of the 2nd "while"
            }

            if (key.isReadable()){
                SocketChannel channel = (SocketChannel) key.channel();
                receiveClientData(channel);
                channel.close();
            }


        } catch (IOException e){
            throw new ConnectionException();
        }
    }
}
