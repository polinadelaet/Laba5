package app.console;

import adapter.LoggerAdapter;
import connection.exception.ConnectionException;
import connectionWorker.ConnectionWorker;
import message.EntityType;
import message.Message;
import message.exception.WrongTypeException;
import query.Query;
import app.queryBuilder.queryBuilder.*;
import app.queryBuilder.queryCreationException.QueryCreationException;
import response.Response;
import response.Status;
import serializer.exception.DeserializationException;
import serializer.exception.SerializationException;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class ConsoleWork {

    //private final PrintWriter printWriter;
    private final BufferedOutputStream bufferedOutputStream;
    private Scanner skan;
    private final Map<String, QueryBuilder> queryBuilderMap = new HashMap<>();
    private final InputStream inputStream;
    private final boolean isOnServer;

    private final ConnectionWorker connectionWorker;
    private final LoggerAdapter loggerAdapter = LoggerAdapter.createDefault(ConsoleWork.class.getSimpleName());


    public ConsoleWork(InputStream inputStream, OutputStream outputStream, ConnectionWorker connectionWorker, boolean isOnServer) {


        //this.printWriter = new PrintWriter(outputStream);
        this.bufferedOutputStream = new BufferedOutputStream(outputStream);
        this.inputStream = inputStream;
        skan = new Scanner(inputStream);
        this.connectionWorker = connectionWorker;
        this.isOnServer = isOnServer;
        if(!isOnServer) {
            queryBuilderMap.put("help", new SimpleQueryBuilder("help", this));
            queryBuilderMap.put("info", new SimpleQueryBuilder("info", this));
            queryBuilderMap.put("show", new SimpleQueryBuilder("show", this));
            queryBuilderMap.put("add", new AddQueryBuilder(this));
            queryBuilderMap.put("update", new UpdateIdQueryBuilder(this));
            queryBuilderMap.put("remove_by_id", new RemoveByIdQueryBuilder(this));
            queryBuilderMap.put("clear", new ClearQueryBuilder(this));
            queryBuilderMap.put("execute_script", new ExecuteScriptQueryBuilder(this));
            queryBuilderMap.put("exit", new ExitQueryBuilderQueryBuilder(this));
            queryBuilderMap.put("insert_at", new InsertAtIndexQueryBuilder(this));
            queryBuilderMap.put("add_if_max", new AddIfMaxQueryBuilder(this));
            queryBuilderMap.put("remove_lower", new RemoveLowerQueryBuilder(this));
            queryBuilderMap.put("count_by_end_date", new CountByEndDateQueryBuilder(this));
            queryBuilderMap.put("filter_by_person", new FilterByPersonQueryBuilder(this));
            queryBuilderMap.put("print_field_descending_end_date", new PrintFieldDescendingEndDateQueryBuilder(this));
        }else {
            queryBuilderMap.put("save", new SaveQueryBuilder(this));
            queryBuilderMap.put("exit", new ExitQueryBuilderQueryBuilder(this));
        }
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
        if (!isOnServer) {
            printLine("Добро пожаловать! Вы можете ввести команду help, " +
                    "чтобы посмотреть доступные команды.");
        }
        while (true) {
            printLine("Введите команду: ");

            try {
                String line = readLine();
                String[] subStrings = line.split(" +");
                if (subStrings.length == 0) {
                    continue;
                }

                if (subStrings[0].equals("exit")) {
                    printLine("Гудбай, Америка.");
                    System.exit(0);
                }

                try {
                    QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);
                    Query query = queryBuilder.create(subStrings);

                    try {
                        connectionWorker.connect();
                        connectionWorker.send(new Message(EntityType.COMMAND_QUERY, Query.dtoOf(query)));
                    } catch (SerializationException e) {
                        loggerAdapter.errorThrowable("Cannot send query", e);
                        printLine("Внутрення ошибка.");
                        continue;
                    } catch (ConnectionException e) {
                        loggerAdapter.errorThrowable("Server unavailable", e);
                        printLine("Сервер недоступен.");
                        continue;
                    }

                    Response response;
                    try {
                        response = connectionWorker.read().getResponse();
                    } catch (WrongTypeException | DeserializationException e) {
                        loggerAdapter.errorThrowable("Cannot read response", e);
                        printLine("Внутрення ошибка.");
                        continue;
                    } catch (ConnectionException e) {
                        loggerAdapter.errorThrowable("Server unavailable", e);
                        printLine("Сервер недоступен.");
                        continue;
                    }

                    if (response.getStatus().equals(Status.OK)) {
                        printLine(response.getMessage() + System.lineSeparator() + "Команда успешно выполнена.");
                    }
                    if (response.getStatus().equals(Status.BAD_REQUEST)) {
                        printLine(response.getMessage());
                    }
                    if (response.getStatus().equals(Status.INTERNAL_ERROR)) {
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
