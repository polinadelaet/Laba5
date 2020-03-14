package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

public class ExitQueryBuilderQueryBuilder extends CompositeQueryBuilder {

    public ExitQueryBuilderQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }

    @Override
    public Query processCreation(String[] subStrings) {
        return new Query("exit", arguments);
    }
}
