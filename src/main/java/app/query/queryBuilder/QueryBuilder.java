package app.query.queryBuilder;
import app.console.ConsoleWork;
import app.query.Query;
import app.query.queryCreationException.QueryCreationException;

public abstract class QueryBuilder {

    protected final ConsoleWork consoleWork;

    public QueryBuilder(ConsoleWork consoleWork) {
        this.consoleWork = consoleWork;
    }

    public abstract Query create(String[] subStrings) throws QueryCreationException;

}
