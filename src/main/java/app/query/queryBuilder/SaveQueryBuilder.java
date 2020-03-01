package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.ArrayList;

public class SaveQueryBuilder extends QueryBuilder {


    public SaveQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    public Query processCreation(String[] subStrings){
        return new Query("save", new ArrayList<String>());
    }
}
