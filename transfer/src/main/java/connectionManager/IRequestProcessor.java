package connectionManager;

import connectionWorker.ConnectionWorker;

public interface IRequestProcessor {
    void process(ConnectionWorker connectionWorker);
}
