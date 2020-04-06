package app.console;

import app.controller.Controller;
import app.query.Query;
import app.query.queryBuilder.*;
import app.query.queryCreationException.QueryCreationException;
import app.response.Response;
import app.response.Status;

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
    private final Controller controller;

    public ConsoleWork(InputStream inputStream, OutputStream outputStream, Controller controller) {

        this.printWriter = new PrintWriter(outputStream);
        skan = new Scanner(inputStream);
        this.controller = controller;

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
        printWriter.flush();
    }

    public void start() {
        while (true) {
            printLine("Введите интересующую команду: ");

            String line = readLine();
            String [] subStrings = line.split(" +");

            if (subStrings.length == 0){
                continue;
            }
            try {
                QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);
                Query query = queryBuilder.create(subStrings);
                System.out.println("запрос создался");
                System.out.println(query.toString());
                Response response = controller.handleQuery(query);

                if (response.getStatus().equals(Status.OK)){
                    printLine(response.getMessage() + System.lineSeparator() +"Команда успешно выполнена.");
                }

                if (response.getStatus().equals(Status.TIME_TO_EXIT)){
                    System.exit(0);
                }

                if (response.getStatus().equals(Status.BAD_REQUEST)){
                    printLine(response.getMessage());
                }

                if (response.getStatus().equals(Status.INTERNAL_SERVER_ERROR)){
                    printLine("Внутренняя ошибка сервера.");
                }
            } catch (NullPointerException | QueryCreationException e) {
                print("Вы неправильно ввели данные, введите еще раз ");
            }
        }
    }
}
