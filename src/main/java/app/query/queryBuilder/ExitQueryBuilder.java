package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.ArrayList;

public class ExitQueryBuilder extends QueryBuilder {

    public ExitQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    public Query processCreation(String[] subStrings){
        return new Query("exit", new ArrayList<String>());
    }
}
