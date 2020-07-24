package console;

import connection.ClientConnection;
import query.queryBuilder.*;
import reer.Kyk;
import response.Kek;
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
        //queryBuilderMap.put("save", new SaveQueryBuilder(this));
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

        printLine("Welcome! You can enter the command help, to see the available commands.");

        while (true) {

            printLine("Enter the command: ");

            try {
                String line = readLine();
                String[] subStrings = line.split(" +");
                if (subStrings.length == 0) {
                    continue;
                }

                if (subStrings[0].equals("exit")) {
                    System.exit(0);
                }

                try {
                    QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);
                    //Query query =
                    //Query query = queryBuilder.create(subStrings);
                    //System.out.println(query.getCommandName().toString());
                    //Response response = clientConnection.writeDataToSocket(query);
                    Kyk kyk = new Kyk("sd", 23);
                    //Kek kek = new Kek("df", 23);
                    clientConnection.writeData(kyk);
                    Response response = null;

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
                        printLine("Internal Server Error.");
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                    print("NQ/You entered data are incorrect, please try again. ");
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                print("You entered data are incorrect, please try again. ");
            }
        }
    }
}
