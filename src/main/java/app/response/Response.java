package app.response;

import java.io.Serializable;

public final class Response implements Serializable {
    private final Status status;
    private final String message;

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
