package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.ArrayList;

public class ClearQueryBuilder extends QueryBuilder {

    public ClearQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    public Query processCreation(String[] subStrings) {
        return new Query("clear", new ArrayList<String>());
    }
}
