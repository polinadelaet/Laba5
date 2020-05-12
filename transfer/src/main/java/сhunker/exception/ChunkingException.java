package сhunker.exception;

public class ChunkingException extends Exception {
    public ChunkingException() {
    }

    public ChunkingException(String message) {
        super(message);
    }

    public ChunkingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChunkingException(Throwable e) {
        super(e);
    }
}
