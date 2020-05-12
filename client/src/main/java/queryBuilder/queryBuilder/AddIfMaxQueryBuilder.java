package queryBuilder.queryBuilder;

import console.ConsoleWork;
import query.Query;

public final class AddIfMaxQueryBuilder extends CompositeQueryBuilder{

    public AddIfMaxQueryBuilder(ConsoleWork consoleWork){
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
        return new Query("add_if_max", arguments);
    }
}
