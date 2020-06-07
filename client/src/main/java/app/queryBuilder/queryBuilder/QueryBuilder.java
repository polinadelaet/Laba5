package app.queryBuilder.queryBuilder;
import app.console.ConsoleWork;
import query.Query;
import app.queryBuilder.queryCreationException.QueryCreationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class QueryBuilder {

    protected final ConsoleWork consoleWork;

    //protected List<String> arguments;
    protected HashMap<String, String> arguments;

    public QueryBuilder(ConsoleWork consoleWork) {
        this.consoleWork = consoleWork;
        //arguments = new ArrayList<>();
        arguments = new HashMap<>();
    }

    protected abstract int getSubStringsLegalLength();

    private void checkLengthSubStrings(String[] subStrings) throws QueryCreationException {
        int legalSubStringsLength = getSubStringsLegalLength();

        if (subStrings.length != legalSubStringsLength) {
            throw new QueryCreationException("Должно быть " + legalSubStringsLength + " аргументов на одной строчке с именем команды.");
        }
    }

    protected abstract Query processCreation(String[] subStrings);

    // Template method шаблонный метод
    public Query create(String[] subStrings) throws QueryCreationException {
        checkLengthSubStrings(subStrings);
        return processCreation(subStrings);
    }
}
