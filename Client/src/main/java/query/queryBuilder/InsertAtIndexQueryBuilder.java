package query.queryBuilder;

import console.ConsoleWork;
import query.Query;

public final class InsertAtIndexQueryBuilder extends CompositeQueryBuilder{
    public InsertAtIndexQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }
    @Override
    public Query processCreation(String [] subStrings) {

        String index = subStrings[1];
        arguments.clear();
        arguments.add(index);

        for (int i = 0; i<10; i++){
            String targetField = nameOfField.get(i);
            consoleWork.printLine(fields.get(targetField));
            readFieldValueIntoArguments(targetField);
        }
        return new Query("insert_at", arguments);
    }
}
