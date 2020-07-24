package app.serverWork;


import app.connection.ConnectionException;
import app.console.ConsoleWork;
import app.controller.Controller;
import app.query.Query;
import app.response.Response;
import reer.Kyk;
import response.Kek;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

    private final Controller controller;
    private final String host = "localhost";
    private final int port = 49999;


    public Server(Controller controller) {
        this.controller = controller;
    }

    public void start () {
        ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, controller);
        new Thread(new Runnable() {
            @Override
            public void run() {
                consoleWork.start();
            }
        }).start();


        try {

            Selector selector = Selector.open();
            consoleWork.printLine("selector is opened.");
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(host, port));
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                consoleWork.printLine("entered into while true.");

                int select = selector.select(); // количество челиков ожидающих соединение
                if (select == 0){
                    System.out.println("==0");
                    return;
                }
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                System.out.println("ключи = " + selectionKeyIterator.toString());
                while (selectionKeyIterator.hasNext()){
                    System.out.println("Зашли в вайл там где хэз некст итератора");
                    Response response = null;
                    SelectionKey key = selectionKeyIterator.next();
                    System.out.println(key.toString());
                    selectionKeyIterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        System.out.println("клиент читаемый ли ключ: " + key.isReadable());
                        //client.register(selector,SelectionKey.OP_READ); // зарегали канал и для сервера и для клиента в одном селекторе
                        client.register(selector, SelectionKey.OP_READ);

                        System.out.println("клиент читаемый ли ключ: " + key.isReadable());
                        //continue;
                    } else if (key.isReadable()) {
                        System.out.println("ключ читаем");
                        SocketChannel channel = (SocketChannel) key.channel();
                       // Query query = readQueryFromSocket(channel);
                        Kyk kyk =  readQueryFromSocket(channel);
                        System.out.println(kyk.toString());
                        System.out.println(kyk.getName()+" ___"+kyk.getAge());
                        //response = controller.handleQuery(query);
                        //System.out.println(response.toString());
                        channel.register(selector, SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        System.out.println("ключ записываем");

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            consoleWork.printLine("ioe exception");
        } catch (ConnectionException e) {
            e.printStackTrace();
        }

    }
    private Kyk readQueryFromSocket(SocketChannel channel) throws ConnectionException {
        ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
        try {
            channel.read(buffer);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Kyk query = (Kyk) objectInputStream.readObject();
            System.out.println(query.getName());

            return query;
        }catch (java.lang.ClassNotFoundException | IOException e){
            e.printStackTrace();
            System.out.println("110");
        }
return null;
        //todo: написать говно для чтения, сюда буфер и тд
    }
}
