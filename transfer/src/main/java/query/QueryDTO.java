package query;


import message.EntityDTO;

import java.io.Serializable;
import java.util.Map;

public final class QueryDTO extends EntityDTO implements Serializable {
    public String commandName;
    public Map<String, String> arguments;

    @Override
    public String toString() {
        return "QueryDTO{" +
                "commandName='" + commandName + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
