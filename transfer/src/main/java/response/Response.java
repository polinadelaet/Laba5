package response;


public class Response {
    private Status status;

    private String answer;

    public Response() {
    }

    public static Response createInternalError() {
        return new Response(Status.INTERNAL_ERROR, "");
    }

    public static Response createSuccessfully() {
        return new Response(Status.OK, "");
    }

    public static Response createResponse(ResponseDTO responseDTO) {
        Status status = Status.getInstance(Integer.parseInt(responseDTO.status));
        return new Response(status,
                            responseDTO.answer);
    }

    public static ResponseDTO dtoOf(Response response) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.status = response.getStatus().getResult() + "";
        responseDTO.answer = response.getAnswer();
        return responseDTO;
    }


    public Response(Status status,
                    String answer) {
        this.status = status;
        this.answer = answer;
    }

    public Status getStatus() {
        return status;
    }

    public String getAnswer() {
        return answer;
    }


    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", answer='" + answer + '\'' +
                '}';
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
