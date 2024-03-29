package query.queryBuilder;

import console.ConsoleWork;
import query.Query;

public final class CountByEndDateQueryBuilder extends CompositeQueryBuilder{

    public CountByEndDateQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }

    @Override
    public Query processCreation(String [] subStrings) {
        arguments.clear();
        String endDate = subStrings[1];

        arguments.add(endDate);
        return new Query("count_by_end_date", arguments);

    }
}
