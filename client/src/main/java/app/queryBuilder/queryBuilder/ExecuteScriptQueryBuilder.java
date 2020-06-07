package app.queryBuilder.queryBuilder;

import app.console.ConsoleWork;
import query.Query;

public final class ExecuteScriptQueryBuilder extends CompositeQueryBuilder {

    public ExecuteScriptQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }

    @Override
    public Query processCreation(String [] subStrings) {
        arguments.clear();
        String file_name = subStrings[1];

        arguments.put("file_name", file_name);
        return new Query("execute_script", arguments);

    }
}
