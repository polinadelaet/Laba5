package app.query.queryBuilder;
import app.console.ConsoleWork;
import app.query.Query;

public abstract class QueryBuilder {

   private final ConsoleWork consoleWork;

    public QueryBuilder(ConsoleWork consoleWork) {
        this.consoleWork = consoleWork;
    }

    public abstract Query create();

}
