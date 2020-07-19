package query.queryBuilder;

import console.ConsoleWork;
import query.Query;

public final class FilterByPersonQueryBuilder extends CompositeQueryBuilder{

    public FilterByPersonQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }

    @Override
    public Query processCreation(String [] subStrings) {

        arguments.clear();
        for (int i = 7; i<10; i++){
            String targetField = nameOfField.get(i);
            consoleWork.printLine(fields.get(targetField));
            readFieldValueIntoArguments(targetField);
        }
        return new Query("filter_by_person", arguments);

    }
}
