package app.query.queryBuilder;
import app.console.ConsoleWork;
import app.query.Query;
import app.query.queryCreationException.QueryCreationException;

public abstract class QueryBuilder {

    protected final ConsoleWork consoleWork;

    public QueryBuilder(ConsoleWork consoleWork) {
        this.consoleWork = consoleWork;
    }

    protected abstract int getSubStringsLegalLength();

    private void checkLengthSubStrings(String[] subStrings) throws QueryCreationException {
        int legalSubStringsLength = getSubStringsLegalLength();

        if (subStrings.length != legalSubStringsLength) {
            throw new QueryCreationException("Должно быть " + legalSubStringsLength + " аргументов на одной строчке с именем команды.");
        }
    }

    protected abstract Query processCreation(String[] subStrings) throws QueryCreationException;

    // Template method шаблонный метод
    public Query create(String[] subStrings) throws QueryCreationException {
        checkLengthSubStrings(subStrings);
        return processCreation(subStrings);
    }
}
