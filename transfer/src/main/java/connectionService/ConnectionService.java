package connectionService;

import connection.exception.ConnectionException;
import message.EntityType;
import message.Message;
import query.Query;
import response.Response;
import response.Status;

/**
 * Welcome to the Transfer Module ver. 1.1!
 *
 * <br></br>
 * <br></br>
 *
 * <b>Connection</b>
 * <br></br>
 * Defines an abstraction on any type internet connection. Support only R\W with bytes.
 * Also provides useful methods for checking is this connection is connected or open.
 * <br></br>
 * <i>Connected</i> means that physical connection with address exists.
 * <br></br>
 * <i>Opened</i> means that the connection is open to consume or produce bytes.
 *
 * <br></br>
 * <br></br>
 *
 * <b>ConnectionWorker</b>
 * <br></br>
 * Defines an wrapper which can send and read {@link Message} of any byte length. It uses
 * {@link serializer.ISerializer} for converting a message into a byte array. After that
 * ConnectionWorker uses {@link сhunker.Chunker} to split a single byte array on
 * several fixed length parts and add a terminate stop chunk. Last step - ConnectionWorker
 * sends each chunk via associated {@link connection.Connection}. When it reads a message,
 * you should read these steps in inverse order.
 *
 * <br></br>
 * <br></br>
 *
 * <b>Message</b>
 * <br></br>
 * An abstraction for transferring data. It stores a dto object of Query or Response. All dtoes
 * have only primitive or String fields.
 *
 * <br></br>
 * <br></br>
 *
 * <b>Query</b>
 * <br></br>
 * Contains a commandName, a map of arguments STRING - STRING and an accessJWT. You can use the accessJWT
 * field to send an access token like a key from your server for users. Read more about JWT if you like.
 *
 * <br></br>
 * <br></br>
 *
 * <b>Response</b>
 * <br></br>
 * Contains a status and an answer. We use http status codes which you can see on Wikipedia.
 *
 * <br></br>
 * <br></br>
 *
 * @since 1.1
 * <b>ConnectionService</b>
 * <br></br>
 * Defines a wrapper for ConnectionWorker which simply reduce code line number :)
 *
 * @version 1.1
 * @author Mr.Kefir and Yorik
 */
public final class ConnectionService {
    private final ConnectionWorker connectionWorker;


    public ConnectionService(ConnectionWorker connectionWorker) {
        this.connectionWorker = connectionWorker;
    }


    public void send(Message message) throws ConnectionException {
        if (!connectionWorker.isConnected()) {
            connectionWorker.connect();
        }

        connectionWorker.send(message);
    }

    public void send(Query query) throws ConnectionException {
        if (!connectionWorker.isConnected()) {
            connectionWorker.connect();
        }

        connectionWorker.send(new Message(EntityType.QUERY, Query.dtoOf(query)));
    }

    public void send(Response response) throws ConnectionException {
        if (!connectionWorker.isConnected()) {
            connectionWorker.connect();
        }

        connectionWorker.send(new Message(EntityType.RESPONSE, Response.dtoOf(response)));
    }

    public Response sendAndGetResponse(Query query) throws ConnectionException {
        if (!connectionWorker.isConnected()) {
            connectionWorker.connect();
        }

        send(query);

        return readResponse();
    }

    public Message readMessage() throws ConnectionException {
        return connectionWorker.read();
    }

    public Response readResponse() throws ConnectionException {
        Message message = connectionWorker.read();
        return message.getResponse();
    }

    public Query readQuery() throws ConnectionException {
        Message message = connectionWorker.read();
        return message.getQuery();
    }


    public void send(String jsonAnswer) throws ConnectionException {
        if (!connectionWorker.isConnected()) {
            connectionWorker.connect();
        }

        Response response = new Response(Status.OK, jsonAnswer);
        send(response);
    }

    public void close() throws ConnectionException {
        connectionWorker.close();
    }

    public boolean isOpen() {
        return connectionWorker.isOpen();
    }
}
