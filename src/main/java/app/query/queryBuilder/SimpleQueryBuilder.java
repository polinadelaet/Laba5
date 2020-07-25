package app.query.queryBuilder;
import app.console.ConsoleWork;
import query.Query;

public class SimpleQueryBuilder extends CompositeQueryBuilder {

    private final String commandName;

    public SimpleQueryBuilder(String commandName, ConsoleWork consoleWork){
        super(consoleWork);
        this.commandName = commandName;
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }

    @Override
    public Query processCreation(String [] subStrings) {
        arguments.clear();
        return new Query(commandName, arguments);
    }
}
