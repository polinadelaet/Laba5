package response;

import java.io.Serializable;

public enum Status implements Serializable {

    OK(200),
    BAD_REQUEST(400),
    INTERNAL_SERVER_ERROR(500),
    TIME_TO_EXIT(138);

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
