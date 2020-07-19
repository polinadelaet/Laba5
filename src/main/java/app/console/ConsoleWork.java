package app.console;

import app.connection.ConnectionException;
import app.connection.ServerConnection;
import app.controller.Controller;
import app.query.Query;
import app.query.queryBuilder.*;
import app.query.queryCreationException.QueryCreationException;
import app.response.Response;
import app.response.Status;

import java.io.*;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.*;

public final class ConsoleWork {

    //private final PrintWriter printWriter;
    private final BufferedOutputStream bufferedOutputStream;
    private Scanner skan;
    private final Map<String, QueryBuilder> queryBuilderMap = new HashMap<>();
    private final InputStream inputStream;
    private final ServerConnection serverConnection;
    private Selector selector;
    private ServerSocketChannel server;


    public ConsoleWork(InputStream inputStream, OutputStream outputStream,
                       ServerConnection serverConnection) {


        //this.printWriter = new PrintWriter(outputStream);
        this.bufferedOutputStream = new BufferedOutputStream(outputStream);
        this.inputStream = inputStream;
        skan = new Scanner(inputStream);
        this.serverConnection = serverConnection;

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
        this.selector = Selector.open();
        this.server = serverConnection.initServerChannel(selector); //создаем канал для сервера

        printLine("Сервер запущен! Доступные команды:" + System.lineSeparator() + "       * exit"
                + System.lineSeparator() + "       * save");
        while (true) {

            try {
                String line = readLine();
                String[] subStrings = line.split(" +");
                if (subStrings.length == 0) {
                    continue;
                }
                try {

                    QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);
                    Query query = queryBuilder.create(subStrings);
                    // запрос мы должны отправить


                    // TODO СЕРВЕРНАЯ ЧАСТЬ

                    // не печатаем а отправляем на клиент
                    Response response = controller.handleQuery(query);// на сервере выполняем

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
                } catch (ConnectionException e){

                }
            } catch (NoSuchElementException e) {
                print("Вы неправильно ввели команду, введите еще раз. ");
            }
        }
    }
}
