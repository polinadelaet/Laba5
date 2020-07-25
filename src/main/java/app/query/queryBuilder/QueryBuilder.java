package app.query.queryBuilder;
import app.console.ConsoleWork;
import app.query.queryCreationException.QueryCreationException;
import query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryBuilder {

    protected final ConsoleWork consoleWork;

    protected List<String> arguments;

    public QueryBuilder(ConsoleWork consoleWork) {
        this.consoleWork = consoleWork;
        arguments = new ArrayList<>();
    }

    protected abstract int getSubStringsLegalLength();

    private void checkLengthSubStrings(String[] subStrings) throws QueryCreationException {
        int legalSubStringsLength = getSubStringsLegalLength();

        if (subStrings.length != legalSubStringsLength) {
            throw new QueryCreationException("Should be " + legalSubStringsLength + " аrguments on one line with the command name.");
        }
    }

    protected abstract Query processCreation(String[] subStrings);

    // Template method шаблонный метод
    public Query create(String[] subStrings) throws QueryCreationException {
        checkLengthSubStrings(subStrings);
        return processCreation(subStrings);
    }
}
