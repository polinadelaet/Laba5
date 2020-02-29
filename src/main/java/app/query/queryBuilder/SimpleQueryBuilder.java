package app.query.queryBuilder;
import app.console.ConsoleWork;
import app.query.Query;

import java.util.ArrayList;

public class SimpleQueryBuilder extends QueryBuilder {

    private final String commandName;

    public SimpleQueryBuilder(String commandName, ConsoleWork consoleWork){
        super(consoleWork);
        this.commandName = commandName;
    }

    @Override
    public Query create(String [] subStrings){
        return new Query(commandName, new ArrayList<String>());
    }
}
