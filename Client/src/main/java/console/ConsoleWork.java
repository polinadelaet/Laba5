package console;

import connection.ClientConnection;
import query.Query;
import query.queryBuilder.*;
import query.queryCreationException.QueryCreationException;
import response.Response;
import response.Status;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class ConsoleWork {

    private final BufferedOutputStream bufferedOutputStream;
    private Scanner skan;
    private final Map<String, QueryBuilder> queryBuilderMap = new HashMap<>();
    private final InputStream inputStream;
    private final ClientConnection clientConnection;


    public ConsoleWork(InputStream inputStream, OutputStream outputStream, ClientConnection clientConnection) {

        this.bufferedOutputStream = new BufferedOutputStream(outputStream);
        this.inputStream = inputStream;
        skan = new Scanner(inputStream);
        this.clientConnection = clientConnection;

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
        byte[] result = string.getBytes();
        try {
            bufferedOutputStream.write(result);
            bufferedOutputStream.flush();
        }catch (java.io.IOException e){
            System.out.println("Ошибка записи.");
        }
    }
    public void printLine(String string){

        string+=System.lineSeparator();
        byte[] result = string.getBytes();

        try {
            bufferedOutputStream.write(result);
            bufferedOutputStream.flush();
        }catch (java.io.IOException e){
            System.out.println("Ошибка записи.");
        }
    }

    public void start() {

        printLine("Добро пожаловать! Вы можете ввести команду help, " +
                "чтобы посмотреть доступные команды.");
        while (true) {
            printLine("Введите команду: ");

            try {
                String line = readLine();
                String[] subStrings = line.split(" +");
                if (subStrings.length == 0) {
                    continue;
                }
                try {

                    QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);
                    Query query = queryBuilder.create(subStrings);
                    clientConnection.writeDataToSocket();
                    Response response = null; //todo дописать и принять респонс от сервера
                    // запрос мы должны отправить
                    // принимаем response
                    if (response.getStatus().equals(Status.OK)) {
                        printLine(response.getMessage() + System.lineSeparator() + "Команда успешно выполнена.");
                    }
                    if (response.getStatus().equals(Status.TIME_TO_EXIT)) {
                        System.exit(0);
                    }
                    if (response.getStatus().equals(Status.BAD_REQUEST)) {
                        printLine(response.getMessage());
                    }
                    if (response.getStatus().equals(Status.INTERNAL_SERVER_ERROR)) {
                        printLine("Внутренняя ошибка сервера.");
                    }

                } catch (NullPointerException | QueryCreationException e) {
                    print("Вы неправильно ввели данные, введите еще раз. ");
                }
            } catch (NoSuchElementException e) {
                print("Вы неправильно ввели данные, введите еще раз. ");
            }
        }
    }
}
