package server;

import adapter.LoggerAdapter;
import connection.SocketChannelConnection;
import connection.exception.ConnectionException;
import connectionService.ConnectionService;
import connectionService.ConnectionWorker;
import middleware.Middleware;
import middleware.MiddlewareException;
import query.Query;
import response.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Server {
    private static final LoggerAdapter LOGGER_ADAPTER = LoggerAdapter.createDefault(Server.class.getSimpleName());


    private final int port;
    private final int connectionBufferSize;

    private final Middleware rootMiddleware;
    private final ExecutorService cachedThreadPool;
    private final ExecutorService fixedThreadPool;


    public Server(int port,
                  int connectionBufferSize,
                  Middleware rootMiddleware,
                  ExecutorService cachedThreadPool,
                  ExecutorService fixedThreadPool) {
        this.port = port;
        this.connectionBufferSize = connectionBufferSize;
        this.rootMiddleware = rootMiddleware;
        this.cachedThreadPool = cachedThreadPool;
        this.fixedThreadPool = fixedThreadPool;
    }


    public void start() throws IOException{
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    logger.errorThrowable(e);
//                    System.exit(1);
//                }
//
//                Connection connection;
//                try {
//                    connection = new SocketConnection("localhost", 8080, 128);
//                } catch (ConnectionException e) {
//                    System.out.println("Все плохо.");
//                    return;
//                }
//
//                ConsoleWork consoleWork = new ConsoleWork(System.in, System.out, ConnectionWorker.createDefault(connection), true);
//                consoleWork.start();
//            }
//        }).start();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            SocketChannelConnection socketChannelConnection = new SocketChannelConnection(socketChannel, connectionBufferSize);
            ConnectionWorker connectionWorker = ConnectionWorker.createDefault(socketChannelConnection);
            ConnectionService connectionService = new ConnectionService(connectionWorker);

            cachedThreadPool.execute(() -> {
                Query query;
                try {
                    query = connectionService.readQuery();

                    fixedThreadPool.execute(() -> {
                        Response response;
                        try {
                            response = rootMiddleware.handle(query);

                            cachedThreadPool.execute(() -> {
                                try {
                                    connectionService.send(response);
                                } catch (ConnectionException e) {
                                    LOGGER_ADAPTER.errorThrowable(e);
                                }
                            });
                        } catch (MiddlewareException e) {
                            LOGGER_ADAPTER.errorThrowable(e);
                            cachedThreadPool.execute(() -> {
                                try {
                                    connectionService.send(Response.createInternalError());
                                } catch (ConnectionException ex) {
                                    LOGGER_ADAPTER.errorThrowable(e);
                                }
                            });
                        }
                    });
                } catch (ConnectionException e) {
                    LOGGER_ADAPTER.errorThrowable(e);
                }
            });
        }
    }
}
