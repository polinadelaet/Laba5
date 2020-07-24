package connection;

import reer.Kyk;
import response.Kek;
import response.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ClientConnection {

    /**
     * потоки ввода-вывода юзать
     */
    private final String host = "localhost";
    private final int port = 49999;



    public Response writeDataToSocket(Kek kek){
        try {
            Selector selector = Selector.open();
            SocketChannel client = SocketChannel.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress(host,port));
            client.register(selector, SelectionKey.OP_CONNECT);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    SocketChannel socketChannel = (SocketChannel) key.channel();

                    if (key.isConnectable()) {
                        System.out.println("подключились к серверу");
                        /*if (socketChannel.isConnectionPending()) {
                            try {
                                System.out.println("enter in govno");
                                socketChannel.finishConnect();
                            } catch (IOException e) {

                            }
                        }

                         */
                        System.out.println("write == " + key.isWritable());
                        socketChannel.register(selector, SelectionKey.OP_WRITE);
                        System.out.println("write == " + key.isWritable());
                        continue;

                    }

                    if (key.isWritable()) {
                        System.out.println(" в канал можно записывать");
                        writeCommandToSocket(socketChannel, kek);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        //continue;
                    }

                    if (key.isReadable()) {
                        System.out.println(" канал можно читать");
                        Response response = readResponceFromSocket(socketChannel);
                        socketChannel.close();
                        return response;
                    }


                }
            }
        } catch (IOException e) {
            System.out.println("IOException | ClosedChannelException");
        }
        return null;
    }

    public void writeData(Kyk kyk) {
        try {
            SocketChannel myClient = SocketChannel.open(new InetSocketAddress(host, port));
            //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            //objectOutputStream.writeObject(query);


            ObjectOutputStream  oos = new ObjectOutputStream(myClient.socket().getOutputStream());
            oos.writeObject(kyk);
            oos.close();


            //objectOutputStream.flush();
            System.out.println("111111111");
            //myClient.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("nnnnnnnnnnnnneeeeeeeeeeetttttt");
        }


    }

    private void writeCommandToSocket(SocketChannel channel, Kek kek) {
        //Query сериализуем
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//todo ошибки обработать
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(kek);
            objectOutputStream.flush();
            channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            //todo
        }
    }

    private Response readResponceFromSocket (SocketChannel socketChannel){
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
