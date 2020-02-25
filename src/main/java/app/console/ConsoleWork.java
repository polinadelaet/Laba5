package app.console;

import app.query.Query;
import app.query.queryBuilder.QueryBuilder;
import app.query.queryBuilder.SimpleQueryBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class ConsoleWork {

    private final InputStream inputStream;
    private final PrintWriter printWriter;
    private final Map<String, QueryBuilder> queryBuilderMap = new HashMap<>();

    public ConsoleWork(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.printWriter = new PrintWriter(outputStream);
        queryBuilderMap.put("help", new SimpleQueryBuilder("help", this));
        queryBuilderMap.put("info", new SimpleQueryBuilder("info", this));
        queryBuilderMap.put("show", new SimpleQueryBuilder("show", this));

    }
    public void start(){

        try (Scanner skan = new Scanner(inputStream))
        {
            while (true) {
                printWriter.println("Введите интересующую команду: ");
                String line = skan.nextLine();
                String [] subStrings = line.split(" +");
                QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);
                Query query = queryBuilder.create();
            }
        }
    }
}
