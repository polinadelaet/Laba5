package query;

import java.io.Serializable;
import java.util.List;

public final class Query implements Serializable {

    private static final long serialVersionUID = 3838L;
    private final String commandName;
    private final List<String> arguments;

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
