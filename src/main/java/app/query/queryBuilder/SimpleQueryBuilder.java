package app.query.queryBuilder;
import app.console.ConsoleWork;
import app.query.Query;

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
        return new Query(commandName, arguments);
    }
}
