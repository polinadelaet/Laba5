package connectionService;

import adapter.LoggerAdapter;
import connection.Connection;
import connection.exception.ConnectionException;
import connection.exception.NotYetConnectedException;
import message.Message;
import serializer.ISerializer;
import serializer.JavaSerializer;
import serializer.exception.DeserializationException;
import serializer.exception.SerializationException;
import serializer.jsonSerializaer.JSONSerializer;
import сhunker.Chunker;
import сhunker.IChunker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ConnectionWorker {
    private static final LoggerAdapter LOGGER_ADAPTER = LoggerAdapter.createDefault(ConnectionWorker.class.getSimpleName());


    private final Connection connection;

    private final ISerializer serializer;
    private final IChunker chunker;


    private ConnectionWorker(Connection connection,
                             ISerializer serializer,
                             IChunker chunker) {
        this.connection = connection;

        this.serializer = serializer;
        this.chunker = chunker;
    }

    public boolean isConnected() {
        return connection.isConnected() && connection.isOpen();
    }

    public void connect() throws ConnectionException {
        connection.connect();
    }

    public static ConnectionWorker createDefault(Connection connection) {
        return createBasedOnJSON(connection);
    }

    public static ConnectionWorker createBasedOnJSON(Connection connection) {
        return new ConnectionWorker(connection,
                                    new JSONSerializer(),
                                    new Chunker(connection.getBufferSize()));
    }

    public static ConnectionWorker createBasedOnJavaObjectStream(Connection connection) {
        return new ConnectionWorker(connection,
                                    new JavaSerializer(),
                                    new Chunker(connection.getBufferSize()));
    }


    public void send(Message message) throws ConnectionException, SerializationException {
        if (!connection.isConnected() || !connection.isOpen()) {
            throw new NotYetConnectedException();
        }

        byte[] bytes = serializer.toByteArray(message);
        List<byte[]> chunks = chunker.split(bytes);

        for (byte[] chunk : chunks) {
            connection.write(chunk);
        }

        LOGGER_ADAPTER.debug("SUCCESSFULLY sent message: " + message);
    }

    public Message read() throws ConnectionException, DeserializationException {
        if (!connection.isConnected() || !connection.isOpen()) {
            throw new NotYetConnectedException();
        }

        List<byte[]> chunks = new ArrayList<>();

        byte[] chunk = connection.read();

        while (!Arrays.equals(chunk, chunker.getKeyWord())) {
            chunks.add(chunk);
            chunk = connection.read();
        }

        byte[] bytes = chunker.join(chunks);

        Message message = serializer.fromByteArray(bytes, Message.class);
        LOGGER_ADAPTER.debug("SUCCESSFULLY read message: " + message);

        return message;
    }

    public void close() throws ConnectionException {
        connection.close();
    }

    public boolean isOpen() {
        return connection.isOpen();
    }
}
