package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AddQueryBuilder extends CompositeQueryBuilder{

    public AddQueryBuilder(ConsoleWork consoleWork){
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
        return new Query("add", arguments);
    }
}
