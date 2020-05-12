package queryBuilder.queryBuilder;

import console.ConsoleWork;
import query.Query;

public final class RemoveByIdQueryBuilder extends CompositeQueryBuilder {

    public RemoveByIdQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }
    @Override
    public Query processCreation(String [] subStrings) {
        arguments.clear();
        String id = subStrings[1];

        arguments.add(id);
        return new Query("remove_by_id", arguments);

    }
}
