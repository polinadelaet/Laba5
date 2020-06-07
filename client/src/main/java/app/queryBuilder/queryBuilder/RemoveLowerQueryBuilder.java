package app.queryBuilder.queryBuilder;

import app.console.ConsoleWork;
import query.Query;

public final class RemoveLowerQueryBuilder extends CompositeQueryBuilder {

    public RemoveLowerQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }

    @Override
    public Query processCreation(String [] subStrings) {

        arguments.clear();
        for (int i = 0; i<10; i++){
            String targetField = nameOfField.get(i);
            consoleWork.printLine(fields.get(targetField));
            readFieldValueIntoArguments(targetField);
        }

        return new Query("remove_lower", arguments);
    }
}
