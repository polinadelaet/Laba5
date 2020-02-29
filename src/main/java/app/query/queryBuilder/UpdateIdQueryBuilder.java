package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.ArrayList;

public class UpdateIdQueryBuilder extends QueryBuilder{

    public UpdateIdQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    public Query create(String [] subStrings) {
        return new Query("updateId", new ArrayList<String>());
    }
}
