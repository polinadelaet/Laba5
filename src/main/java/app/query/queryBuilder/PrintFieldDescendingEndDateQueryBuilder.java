package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

public final class PrintFieldDescendingEndDateQueryBuilder extends CompositeQueryBuilder {

    public PrintFieldDescendingEndDateQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }

    @Override
    public Query processCreation(String [] subStrings) {

        return new Query("print_field_descending_end_date", arguments);

    }
}
