package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

public class InsertAtIndex extends QueryBuilder{
    public InsertAtIndex(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    public Query create(){
        return new Query();
    }
}
