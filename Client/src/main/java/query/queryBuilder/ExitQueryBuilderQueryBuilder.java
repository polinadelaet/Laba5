package query.queryBuilder;

import console.ConsoleWork;
import query.Query;

public final class ExitQueryBuilderQueryBuilder extends CompositeQueryBuilder {

    public ExitQueryBuilderQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }

    @Override
    public Query processCreation(String[] subStrings) {
        arguments.clear();
        return new Query("exit", arguments);
    }
}
