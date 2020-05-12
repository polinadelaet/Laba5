package сhunker;

import java.util.List;

public interface IChunker {
    List<byte[]> split(byte[] inputArray);

    byte[] getKeyWord();

    byte[] join(List<byte[]> chunks);
}
