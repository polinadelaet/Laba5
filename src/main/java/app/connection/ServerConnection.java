package app.connection;

import app.controller.Controller;
import app.query.Query;
import app.response.Response;
import app.response.Status;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ServerConnection {
    private final Controller controller;
    private final String host = "localhost";
    private final int port = 50000;
    //private final ConnectionWorker connectionWorker;
    private static final Logger logger = LogManager.getLogger(ServerConnection.class);


    public ServerConnection(Controller controller) {
        this.controller = controller;
    }

    public void connect(Selector selector, ServerSocketChannel server) throws ConnectionException {
        try {

            while (true){

                int select = selector.select(); // количество челиков ожидающих соединение

                if (select == 0){
                    continue;
                }

                Iterator <SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();

                while (selectionKeyIterator.hasNext()){

                    Response response = null;
                    SelectionKey key = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    if (key.isAcceptable()){
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_READ); // зарегали канал и для сервера и для клиента в одном селекторе
                        //TODO we should send to server message that connection is created

                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        Query query = readQueryFromSocket(channel);
                        response = controller.handleQuery(query);
                        channel.register(selector, SelectionKey.OP_WRITE);
                    }

                    if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        writeDataToSocket(channel, response);
                        channel.close();
                        return;
                    }
                }
            }
        } catch (IOException e){

            throw new ConnectionException();
        }
    }

    public ServerSocketChannel initServerChannel(Selector selector) throws ConnectionException {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(host, port));
            server.register(selector, SelectionKey.OP_ACCEPT);
            return server;
        } catch (IOException e){
            // TODO logger.
            throw new ConnectionException();
        }
    }
    private Query readQueryFromSocket(SocketChannel channel){
        //зарегать как write
        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
       try {
           channel.read(buffer);
           ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
           ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
           Query query = (Query) objectInputStream.readObject();
           buffer.clear();
           return query;
       }catch (IOException e){}
        catch (java.lang.ClassNotFoundException e){}
        //todo: написать говно для чтения, сюда буфер и тд
    }

    private void writeDataToSocket(SocketChannel channel, Response response){
        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
        channel.write(buffer);

    }
    private String responseToAnswer(Response response){
        String answer = " ";
        if (response.getStatus().equals(Status.OK)) {
            return answer = response.getMessage() + System.lineSeparator() + "Команда успешно выполнена.";
        }
        if (response.getStatus().equals(Status.TIME_TO_EXIT)) {
            System.exit(0);
        }
        if (response.getStatus().equals(Status.BAD_REQUEST)) {
            printLine(response.getMessage());
        }
        if (response.getStatus().equals(Status.INTERNAL_SERVER_ERROR)) {
            printLine("Внутренняя ошибка сервера.");
        }

    }
}
