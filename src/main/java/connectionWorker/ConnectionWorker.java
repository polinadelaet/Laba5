package connectionWorker;

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
    private final Connection connection;

    private final ISerializer serializer;
    private final IChunker chunker;

    private final LoggerAdapter loggerAdapter;


    private ConnectionWorker(Connection connection,
                             ISerializer serializer,
                             IChunker chunker) {
        loggerAdapter = LoggerAdapter.createDefault(ConnectionWorker.class.getSimpleName());

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
//        loggerAdapter.debug("Got bytes from Serializer, size: " + bytes.length);
//        loggerAdapter.debug("Got bytes from Serializer: " + Arrays.toString(bytes));
        List<byte[]> chunks = chunker.split(bytes);
//        loggerAdapter.debug("Got chunks form Chunker, list size: " + chunks.size());

        for (byte[] chunk : chunks) {
//            loggerAdapter.debug("Chunk: " + Arrays.toString(chunk));
            connection.write(chunk);
        }

        loggerAdapter.info("SUCCESSFULLY sent message.");
    }

    public Message read() throws ConnectionException, DeserializationException {
        if (!connection.isConnected() || !connection.isOpen()) {
            throw new NotYetConnectedException();
        }

        List<byte[]> chunks = new ArrayList<>();

        byte[] chunk = connection.read();

        while (!Arrays.equals(chunk, chunker.getKeyWord())) {
//            loggerAdapter.info((new String(chunk, StandardCharsets.UTF_8)).trim());
//            loggerAdapter.debug("Chunk: " + Arrays.toString(chunk));
            chunks.add(chunk);
            chunk = connection.read();
        }

//        loggerAdapter.debug("Got chunks from client, size: " + chunks.size());
//        loggerAdapter.debug("First chuck: " + Arrays.toString(chunks.get(0)));

        byte[] bytes = chunker.join(chunks);
//        loggerAdapter.debug((new String(bytes, StandardCharsets.UTF_8)).trim());
//        loggerAdapter.debug("Join chunks, bytes size: " + bytes.length);

        Message message = serializer.fromByteArray(bytes, Message.class);
        loggerAdapter.info("SUCCESSFULLY read message.");

        return message;
    }
}
