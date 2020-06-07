package query;

import java.util.Map;

/**
 * Instances of that class have all information about input commands with arguments.
 * Each command matches to one instance of that class.
 */
public final class Query {
    private final String commandName;
    private final Map<String, String> arguments;


    public static Query createQuery(QueryDTO queryDTO) {
        return new Query(queryDTO.commandName,
                         queryDTO.arguments);
    }

    public static QueryDTO dtoOf(Query query) {
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.commandName = query.commandName;
        queryDTO.arguments = query.arguments;

        return queryDTO;
    }

    public Query(String commandName,
                 Map<String, String> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "Query{" +
                "commandName='" + commandName + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
