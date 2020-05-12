package response;

public enum Status {
    CONTINUE           (100),

    OK                 (200),
    CREATED            (201),
    FAIL               (228),
    INSUFFICIENT_STORAGE(507),

    FOUND              (302),
    GO_BACK            (306),

    BAD_REQUEST        (400),
    FORBIDDEN          (403),
    PRECONDITION_FAILED(412),
    I_AM_TEAPOT        (418),

    INTERNAL_ERROR     (500);

    private final int statusCode;

    Status(int statusCode) {
        this.statusCode = statusCode;
    }

    public static Status getInstance(int statusCode){

        for (Status status: values()){
            if (statusCode == status.statusCode){
                return status;
            }
        }
        return null;
    }
}
