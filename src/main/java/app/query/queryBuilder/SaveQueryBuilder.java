package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;


public class SaveQueryBuilder extends CompositeQueryBuilder{


    public SaveQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 1;
    }
    @Override
    public Query processCreation(String[] subStrings) {
        return new Query("save", arguments);
    }
}
