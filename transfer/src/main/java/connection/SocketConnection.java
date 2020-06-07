package connection;

import connection.exception.ConnectionException;
import connection.exception.ReadingException;
import connection.exception.WritingException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public final class SocketConnection extends Connection{
    private Socket socket;


    public SocketConnection(String address,
                            int port,
                            int bufferSize) throws ConnectionException {
        super(address, port, bufferSize);

        socket = new Socket();
    }

    public SocketConnection(Socket socket,
                            int bufferSize) {
        super(bufferSize);
        this.socket = socket;
    }

    @Override
    public void connect() throws ConnectionException {
        try {
            socket.connect(new InetSocketAddress(address, port));
            socket.setSoTimeout(60000);
        } catch (IOException e) {
            socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(address, port));
            } catch (IOException ex) {
                throw new ConnectionException(ex);
            }
        }
    }

    @Override
    public void write(byte[] bytes) throws WritingException {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            throw new WritingException(e);
        }
    }

    @Override
    public byte[] read() throws ReadingException {
        int quantityOfReadBytes;
        try {
            InputStream inputStream = socket.getInputStream();
            quantityOfReadBytes = inputStream.read(outBuffer.array());
            //socket.setSoTimeout(5000);
        } catch (IOException e) {
            try {
                socket.close();
                outBuffer = ByteBuffer.allocate(bufferSize);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            throw new ReadingException(e);
        }

        if (quantityOfReadBytes == -1) {
            try {
                socket.close();
                outBuffer = ByteBuffer.allocate(bufferSize);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            throw new ReadingException("Reached an end of inputStream: ");
        }

        byte[] bytes = new byte[bufferSize];
        outBuffer.rewind();
        outBuffer.get(bytes, 0, quantityOfReadBytes);
        outBuffer.clear();

        return bytes;
    }

    @Override
    public void close() throws ConnectionException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    @Override
    public boolean isConnected() {
        return isOpen() && socket.isConnected();
    }

    @Override
    public boolean isOpen() {
        return !socket.isClosed();
    }
}
