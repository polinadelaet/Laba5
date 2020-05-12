package response;

public final class Response {
    private final Status status;
    private final String message;

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseDTO dtoOf(Response response) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.status = response.status.toString();
        responseDTO.answer = response.message;

        return responseDTO;
    }

    public static Response of(ResponseDTO responseDTO) {
        return new Response(Status.valueOf(responseDTO.status), responseDTO.answer);
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
