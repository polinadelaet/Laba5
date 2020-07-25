package app.console;


import app.controller.Controller;
import app.query.queryBuilder.*;
import app.query.queryCreationException.QueryCreationException;
import query.Query;
import response.Response;
import response.Status;

import java.io.*;
import java.util.*;

public final class ConsoleWork {

    private final BufferedOutputStream bufferedOutputStream;
    private Scanner skan;
    private final Map<String, QueryBuilder> queryBuilderMap = new HashMap<>();
    private final InputStream inputStream;
    private final Controller controller;

    public ConsoleWork(InputStream inputStream, OutputStream outputStream, Controller controller) {

        this.bufferedOutputStream = new BufferedOutputStream(outputStream);
        this.inputStream = inputStream;
        skan = new Scanner(inputStream);
        this.controller = controller;

        queryBuilderMap.put("save", new SaveQueryBuilder(this));
        queryBuilderMap.put("exit", new ExitQueryBuilderQueryBuilder(this));

    }

    public String readLine() {
        return skan.nextLine();
    }


    public void print(String string) {
        byte[] result = string.getBytes();
        try {
            bufferedOutputStream.write(result);
            bufferedOutputStream.flush();
        } catch (java.io.IOException e) {
            System.out.println("Ошибка записи.");
        }
    }

    public void printLine(String string) {

        string += System.lineSeparator();
        byte[] result = string.getBytes();

        try {
            bufferedOutputStream.write(result);
            bufferedOutputStream.flush();
        } catch (java.io.IOException e) {
            System.out.println("Ошибка записи.");
        }
    }

    public void start() {

        printLine("Server is running! Available commands:" + System.lineSeparator() + "       * exit"
                + System.lineSeparator() + "       * save");

        while (true) {

            String line = readLine();
            String[] subStrings = line.split(" +");
            if (subStrings.length == 0) {
                continue;
            }
            QueryBuilder queryBuilder = queryBuilderMap.get(subStrings[0]);
            try {
                Query query = queryBuilder.create(subStrings);
                Response response = controller.handleQuery(query);
                getResponse(response);
            } catch (QueryCreationException | NullPointerException e) {
                printLine( "There is no such command.");
            }
        }
    }
    private void getResponse(Response response){
        if (response.getStatus().equals(Status.OK)) {
            printLine(response.getMessage() + System.lineSeparator() + "Command executed successfully.");
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
    }
}