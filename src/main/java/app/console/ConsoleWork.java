package app.console;

import app.query.Query;
import app.query.queryBuilder.*;
import app.query.queryCreationException.QueryCreationException;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

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
        queryBuilderMap.put("update", new UpdateIdQueryBuilder(this));
        queryBuilderMap.put("remove_by_id", new RemoveByIdQueryBuilder(this));
        queryBuilderMap.put("clear", new ClearQueryBuilder(this));
        queryBuilderMap.put("save", new SaveQueryBuilder(this));
        queryBuilderMap.put("execute_script", new ExecuteScriptQueryBuilder(this));
        queryBuilderMap.put("exit", new ExitQueryBuilderQueryBuilder(this));
        queryBuilderMap.put("insert_at", new InsertAtIndexQueryBuilder(this));
        queryBuilderMap.put("add_if_max", new AddIfMaxQueryBuilder(this));
        queryBuilderMap.put("remove_lower", new RemoveLowerQueryBuilder(this));
        queryBuilderMap.put("count_by_end_date", new CountByEndDateQueryBuilder(this));
        queryBuilderMap.put("filter_by_person", new FilterByPersonQueryBuilder(this));
        queryBuilderMap.put("print_field_descending_end_date", new PrintFieldDescendingEndDateQueryBuilder(this));

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
            //if (line == "") line = null;
            String [] subStrings = line.split(" +");
            QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);

            try {
                Query query = queryBuilder.create(subStrings);
                //TODO: 1) передать запрос контроллеру
                //TODO: 2) получить Response от контроллера
                //TODO: 3) обработать Response.
                // Если ошибка внутренняя, просто асбтрактное сообщение об ошибке.
                // Если статус время выхода, завершить приложение.
                // Если ошибка ввода, вывести подробное сообщение, описывающее проблему.
                // Если все ок, вывести сообщение, что команда успешно выполнена.
            } catch (QueryCreationException e) {
                print("Вы неправильно ввели данные, введите еще раз ");
                continue;
            }
        }
    }
}
