package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

public class PrintFieldDescendingEndDateQueryBuilder extends CompositeQueryBuilder {

    public PrintFieldDescendingEndDateQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }

    @Override
    public Query processCreation(String [] subStrings) {

        String endDate = subStrings[1];

        arguments.add(endDate);
        return new Query("print_field_descending_end_date", arguments);

    }
}
