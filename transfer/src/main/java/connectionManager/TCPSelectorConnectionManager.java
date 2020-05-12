package connectionManager;

import adapter.LoggerAdapter;
import connection.SocketChannelConnection;
import connectionManager.exception.ConnectionManagerException;
import connectionWorker.ConnectionWorker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public final class TCPSelectorConnectionManager extends SelectorConnectionManager {
    private final LoggerAdapter loggerAdapter;


    public TCPSelectorConnectionManager(int connectionBufferSize,
                                        int port) {
        super(connectionBufferSize, port);
        loggerAdapter = LoggerAdapter.createDefault(TCPSelectorConnectionManager.class.getSimpleName());
    }

    @Override
    public void openBaseChannel(Selector selector) throws ConnectionManagerException {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, serverSocketChannel.validOps());
            loggerAdapter.info("ServerSocketChannel was SUCCESSFULLY opened on the port: " + port);
        } catch (IOException e) {
            loggerAdapter.fatalThrowable("Cannot open ServerSocketChannel.", e);
            throw new ConnectionManagerException(e);
        }
    }

    @Override
    public void processSelectionKeys(Iterator<SelectionKey> selectionKeyIterator) throws IOException {
        while(selectionKeyIterator.hasNext()) {
            SelectionKey selectionKey = selectionKeyIterator.next();

            if (selectionKey.isAcceptable()) {
                acceptNewClient(selectionKey);
            }

            if (selectionKey.isReadable()) {
                handleClientRequest(selectionKey);
            }

            selectionKeyIterator.remove();
        }
    }

    private void acceptNewClient(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        SocketChannelConnection socketChannelConnection = new SocketChannelConnection(socketChannel,
                                                                                      connectionBufferSize);
        ConnectionWorker connectionWorker = ConnectionWorker.createDefault(socketChannelConnection);

        socketChannel.register(selectionKey.selector(), socketChannel.validOps(), connectionWorker);
        loggerAdapter.info("New client accepted: " + socketChannel.getRemoteAddress());
    }

    private void handleClientRequest(SelectionKey selectionKey) {
        ConnectionWorker connectionWorker = (ConnectionWorker) selectionKey.attachment();
        requestProcessor.process(connectionWorker);
    }
}
