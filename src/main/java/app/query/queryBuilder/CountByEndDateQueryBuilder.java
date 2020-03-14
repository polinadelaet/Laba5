package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

public class CountByEndDateQueryBuilder extends CompositeQueryBuilder{

    public CountByEndDateQueryBuilder(ConsoleWork consoleWork){
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
        return new Query("count_by_end_date", arguments);

    }
}
