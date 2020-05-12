package queryBuilder.queryBuilder;

import console.ConsoleWork;
import query.Query;


public final class UpdateIdQueryBuilder extends CompositeQueryBuilder{

    public UpdateIdQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }
    @Override
    public Query processCreation(String [] subStrings) {

        arguments.clear();
        String id = subStrings[1];

        arguments.add(id);

        for (int i = 0; i<10; i++){
            String targetField = nameOfField.get(i);
            consoleWork.printLine(fields.get(targetField));
            readFieldValueIntoArguments(targetField);
        }

        return new Query("update", arguments);
    }
}
