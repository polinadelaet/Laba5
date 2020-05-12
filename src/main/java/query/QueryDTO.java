package query;


import message.EntityDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public final class QueryDTO extends EntityDTO implements Serializable {
    public String commandName;
    //public Map<String, String> arguments;
    public List<String> arguments;
    public String accessJWT;
}
