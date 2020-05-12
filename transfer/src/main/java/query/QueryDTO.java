package query;


import message.EntityDTO;

import java.io.Serializable;
import java.util.List;

public final class QueryDTO extends EntityDTO implements Serializable {
    public String commandName;
    public List<String> arguments;
    public String accessJWT;
}
