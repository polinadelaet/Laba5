package query;

import java.util.List;
import java.util.Map;

/**
 * Instances of that class have all information about input commands with arguments.
 * Each command matches to one instance of that class.
 */
public final class Query {
    private final String commandName;
    //private final Map<String, String> arguments;
    private final List<String> arguments;
    private final String accessJWT;


    public static Query createQuery(QueryDTO queryDTO) {
        return new Query(queryDTO.commandName,
                         queryDTO.arguments,
                         queryDTO.accessJWT);
    }

    public static QueryDTO getQueryDTO(Query query) {
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.commandName = query.commandName;
        queryDTO.arguments = query.arguments;
        queryDTO.accessJWT = query.accessJWT;

        return queryDTO;
    }

    public Query(String commandName,
                 //Map<String, String> arguments,
                 List<String> arguments,
                 String accessToken) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.accessJWT = accessToken;
    }

    public String getCommandName() {
        return commandName;
    }

    /*public Map<String, String> getArguments() {
        return arguments;
    }*/
    public List<String> getArguments() {
        return arguments;
    }

    public String getAccessJWT() {
        return accessJWT;
    }

    @Override
    public String toString() {
        return "Query{" +
                "commandName='" + commandName + '\'' +
                ", arguments=" + arguments +
                ", accessJWT='" + accessJWT + '\'' +
                '}';
    }
}
