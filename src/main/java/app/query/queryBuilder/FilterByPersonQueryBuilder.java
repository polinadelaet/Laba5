package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

public class FilterByPersonQueryBuilder extends CompositeQueryBuilder{

    public FilterByPersonQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }

    @Override
    public Query processCreation(String [] subStrings) {

        String person = subStrings[1];

        arguments.add(person);
        return new Query("filter_by_person", arguments);

    }
}
