package сhunker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Chunker implements IChunker{
    private final int chunkSize;
    private static final byte[] KEY_WORD =
            new byte[]{34, 36, 83, 84, 79, 80, 95, 84, 82, 65, 78, 83, 70, 69, 82, 82, 73, 78, 71, 36, 34};


    public Chunker(int chunkSize) {
        this.chunkSize = chunkSize;
    }


    /**
     * This method is used to convert inputArray to a list of chunks, and added
     * key word to the end.
     *
     * @param inputArray
     * @return List of chunks
     */
    @Override
    public List<byte[]> split(byte[] inputArray) {
        List<byte[]> chunks = new ArrayList<>();

        for (int index = 0; index < inputArray.length; index += chunkSize) {
            if (inputArray.length - index < chunkSize) {
                byte[] bytes = new byte[chunkSize];
                for (int indexx = 0; indexx < inputArray.length - index; indexx++) {
                    bytes[indexx] = inputArray[index + indexx];
                }
                chunks.add(bytes);
                break;
            }

            chunks.add(Arrays.copyOfRange(inputArray, index, index + chunkSize));
        }

        chunks.add(getKeyWord());

        return chunks;
    }

    /**
     * This method is used to get "stop chunk", which shows us
     * end of transferring query to server.
     *
     * @return KEY_WORD
     */
    @Override
    public byte[] getKeyWord() {
        byte[] stopChunk = new byte[chunkSize];
        for (int index = 0; index < KEY_WORD.length; index++) {
            stopChunk[index] = KEY_WORD[index];
        }
        return stopChunk;
    }

    /**
     * This method joins all chunks to one array.
     *
     * @param chunks
     * @return outputArray
     */
    @Override
    public byte[] join(List<byte[]> chunks) {

        byte[] outputArray = new byte[chunkSize * chunks.size()];

        for (int iteration = 0; iteration < chunks.size(); iteration++) {
            for (int index = 0; index < chunkSize; index++) {
                outputArray[index + iteration * chunkSize] = chunks.get(iteration)[index];
            }
        }

        return outputArray;
    }
}
