package connectionManager;

import adapter.LoggerAdapter;
import connectionManager.exception.ConnectionManagerException;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public abstract class SelectorConnectionManager extends ConnectionManager {
    private final LoggerAdapter loggerAdapter;


    public SelectorConnectionManager(int connectionBufferSize,
                                     int port) {
        super(connectionBufferSize, port);
        loggerAdapter = LoggerAdapter.createDefault(SelectorConnectionManager.class.getSimpleName());
    }

    @Override
    public void processStarting() throws ConnectionManagerException {
        Selector selector = openSelector();

        openBaseChannel(selector);

        loggerAdapter.info("ConnectionManager was SUCCESSFULLY started.");

        while(isStarted) {
            try {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

                processSelectionKeys(selectionKeyIterator);
            } catch (IOException e) {
                throw new ConnectionManagerException(e);
            }
        }
    }

    private Selector openSelector() throws ConnectionManagerException {
        try {
            return Selector.open();
        } catch (IOException e) {
            throw new ConnectionManagerException(e);
        }
    }

    public abstract void openBaseChannel(Selector selector) throws ConnectionManagerException;

    public abstract void processSelectionKeys(Iterator<SelectionKey> selectionKeyIterator) throws IOException;
}
