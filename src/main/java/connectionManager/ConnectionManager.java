package connectionManager;

import connectionManager.exception.ConnectionManagerException;

public abstract class ConnectionManager {
    protected IRequestProcessor requestProcessor;
    protected final int connectionBufferSize;
    protected final int port;
    protected boolean isStarted = false;


    public ConnectionManager(int connectionBufferSize, int port) {
        this.connectionBufferSize = connectionBufferSize;
        this.port = port;
    }

    /**
     * This method must be called by server to start listening the port.
     */
    public final void start(IRequestProcessor requestProcessor) throws ConnectionManagerException {
        this.requestProcessor = requestProcessor;
        isStarted = true;
        processStarting();
    }

    public final void stop() {
        isStarted = false;
    }

    public abstract void processStarting() throws ConnectionManagerException;
}
