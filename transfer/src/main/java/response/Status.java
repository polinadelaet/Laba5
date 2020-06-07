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

    INTERNAL_ERROR     (500);


    private int result;


    Status(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public String getStringResult() {
        return result + "";
    }

    public static Status getInstance(int result) {
        Status[] statuses = values();

        for (Status status : statuses) {
            if (status.result == result) {
                return status;
            }
        }

        return null;
    }
}