package response;

import message.EntityDTO;

import java.io.Serializable;

public final class ResponseDTO extends EntityDTO implements Serializable {
    public String status;
    public String answer;
}
