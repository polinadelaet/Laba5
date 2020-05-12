package connection;

import connection.exception.ConnectionException;

import java.io.IOException;
import java.nio.channels.Channel;

public abstract class ChannelConnection extends Connection {
    protected Channel channel;

    /**
     * Specify address to connect and bufferSize to bytes to be read by read method.
     */
    public ChannelConnection(String address, int port, int bufferSize) throws ConnectionException {
        super(address, port, bufferSize);
    }

    public ChannelConnection(Channel channel, int bufferSize) {
        super(bufferSize);
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public final boolean isOpen() {
        return channel.isOpen();
    }

    @Override
    public void close() throws ConnectionException {
        try {
            channel.close();
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }
}
