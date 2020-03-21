package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.Map;

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

        for (Map.Entry<String, String> entry : fields.entrySet()){
            String targetField = entry.getKey();
            readFieldValueIntoArguments(targetField);
        }

        return new Query("add_if_max", arguments);
    }
}
