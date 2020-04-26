package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

public final class ClearQueryBuilder extends CompositeQueryBuilder {

    public ClearQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }
    @Override
    public Query processCreation(String[] subStrings) {
        arguments.clear();
        return new Query("clear", arguments);
    }
}
