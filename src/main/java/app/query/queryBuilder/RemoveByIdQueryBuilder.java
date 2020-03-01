package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;
import app.query.queryCreationException.QueryCreationException;

import java.util.ArrayList;

public class RemoveByIdQueryBuilder extends QueryBuilder {

    public RemoveByIdQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    public Query processCreation(String [] subStrings) throws QueryCreationException {
        if (subStrings.length != 2) {
            throw new QueryCreationException("Вам нужно ввести ТОЛЬКО один аргумент");
        }

        String id = subStrings[1];
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add(id);
        return new Query("removeById", arguments);

    }
}
