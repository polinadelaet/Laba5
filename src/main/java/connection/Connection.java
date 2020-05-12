package connection;

import connection.exception.ConnectionException;
import connection.exception.ReadingException;
import connection.exception.WritingException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public abstract class Connection {
    protected final InetAddress address;
    protected final int port;
    protected final int bufferSize;

    protected ByteBuffer outBuffer;


    /**
     * Specify address to connect and bufferSize to bytes to be read by read method.
     */
    public Connection(String address, int port, int bufferSize) throws ConnectionException {
        try {
            this.address = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new ConnectionException(e);
        }
        this.port = port;
        this.bufferSize = bufferSize;
        outBuffer = ByteBuffer.allocate(bufferSize);
    }

    public Connection(int bufferSize) {
        address = null;
        port = 0;
        this.bufferSize = bufferSize;
        outBuffer = ByteBuffer.allocate(bufferSize);
    }


    public InetAddress getAddress() {
        return address;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * Connect by address mentioned in the constructor.
     */
    public abstract void connect() throws ConnectionException;

    /**
     * Send N bytes.
     *
     * @param bytes - can have any length.
     */
    public abstract void write(byte[] bytes) throws WritingException;

    /**
     * Read bufferSize bytes.
     *
     * @return the length of byte is the bufferSize mentioned in the constructor.
     */
    public abstract byte[] read() throws ReadingException;

    public abstract void close() throws ConnectionException;

    public abstract boolean isConnected();

    public abstract boolean isOpen();
}
