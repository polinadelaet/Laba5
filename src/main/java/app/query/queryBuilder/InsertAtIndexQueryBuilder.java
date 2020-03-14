package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.Map;

public class InsertAtIndexQueryBuilder extends CompositeQueryBuilder{
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
        arguments.add(index);

        for (Map.Entry<String, String> entry : fields.entrySet()){
            String targetField = entry.getKey();
            readFieldValueIntoArguments(targetField);
        }
        return new Query("insert_at", arguments);
    }
}
