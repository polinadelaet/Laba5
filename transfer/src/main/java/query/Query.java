package query;

import java.util.List;

public final class Query {

    private final String commandName;
    private final List<String> arguments;


    public static Query of(QueryDTO queryDTO) {
        return new Query(queryDTO.commandName, queryDTO.arguments);
    }

    public static QueryDTO dtoOf(Query query) {
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.commandName = query.getCommandName();
        queryDTO.arguments = query.getArguments();

        return queryDTO;
    }


    public Query(String commandName, List<String> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getArguments() {
        return arguments;
    }
}
