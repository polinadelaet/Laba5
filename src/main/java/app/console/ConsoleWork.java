package app.console;

import app.query.Query;
import app.query.queryBuilder.*;
import app.query.queryCreationException.QueryCreationException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class ConsoleWork {

    private final PrintWriter printWriter;
    private final Scanner skan;
    private final Map<String, QueryBuilder> queryBuilderMap = new HashMap<>();

    public ConsoleWork(InputStream inputStream, OutputStream outputStream) {

        this.printWriter = new PrintWriter(outputStream);
        skan = new Scanner(inputStream);
        queryBuilderMap.put("help", new SimpleQueryBuilder("help", this));
        queryBuilderMap.put("info", new SimpleQueryBuilder("info", this));
        queryBuilderMap.put("show", new SimpleQueryBuilder("show", this));
        queryBuilderMap.put("add", new AddQueryBuilder(this));
        queryBuilderMap.put("updateId", new UpdateIdQueryBuilder(this));
        queryBuilderMap.put("clear", new ClearQueryBuilder(this));
        queryBuilderMap.put("save", new SaveQueryBuilder(this));



    }
    public String readLine(){

        return skan.nextLine();

    }
    public void print(String string){
        printWriter.print(string);
    }
    public void printLine(String string){
        printWriter.println(string);
    }

    public void start() {
        while (true) {

            printLine("Введите интересующую команду: ");
            String line = readLine();
            String [] subStrings = line.split(" +");
            QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);

            try {
                Query query = queryBuilder.create(subStrings);
            } catch (QueryCreationException e) {
                print("Вы неправильно ввели данные, введите еще раз ");
                continue;
            }
        }
    }
}
