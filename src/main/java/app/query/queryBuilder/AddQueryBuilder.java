package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;
import app.query.queryCreationException.QueryCreationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddQueryBuilder extends QueryBuilder{

    private final List<String> arguments;
    private final String name = "name";
    private final String coordinateX = "coordinate x";
    private final String coordinateY = "coordinate y";
    private final String salary = "salary";
    private final String startDate = "startDate";
    private final String endDate = "endDate";
    private final String status = "status";
    private final String personWeight = "person weight";
    private final String personHairColor = "person hairColor";
    private final String personCountry = "person country";
    private final Map<String, String> fields = new HashMap<String,String>(){{
        put(name, "Введите имя: ");
        put(coordinateX, "Введите вещественное число: ");
        put(coordinateY, "Введите целое число: ");
        put(salary, "Введите заработную плату: ");
        put(startDate, "Введите дату начала: ");
        put(endDate, "Введите дату окончания: ");
        put(status, "Выберите статус из " +
                        "HIRED" + System.lineSeparator() +
                        "REGULAR" + System.lineSeparator() +
                        "PROBATION" + System.lineSeparator());
        put(personWeight, "Введите вещественное число: ");
        put(personHairColor, "Выберите цвет волос из " +
                        "GREEN" + System.lineSeparator() +
                        "YELLOW" + System.lineSeparator() +
                        "WHITE" + System.lineSeparator() +
                        "BROWN" + System.lineSeparator());
        put(personCountry, "Введите страну из " +
                        "RUSSIA" + System.lineSeparator() +
                        "INDIA" + System.lineSeparator() +
                        "ITALY" + System.lineSeparator());
    }};
    public AddQueryBuilder(ConsoleWork consoleWork){
       super(consoleWork);
       arguments = new ArrayList<>();
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 0;
    }

    @Override
    public Query processCreation(String [] subStrings) throws QueryCreationException {
        for (Map.Entry<String, String> entry : fields.entrySet()) {

            consoleWork.printLine(entry.getValue());
            String userInput;

//TODO нормально сделать проверку данных и вывод
//TODO ________________________________________________________переделать с новыми методами______________________________________________________________
    //WHILE МОЖНО в отдельный метод с параметром String entry.getKey
            while (true) {
                userInput = consoleWork.readLine();

                if (entry.getKey().equals(name)) {
                    if (CheckQuery.invalidName(userInput)) {
                        consoleWork.print("Неверные данные, имя должно быть не пустой строкой, не должно быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(coordinateX)) {
                    if (CheckQuery.invalidCoordinateX(userInput)){
                        consoleWork.print("Неверные данные, координата Х должна быть целым числом, не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(coordinateY)) {
                    if (CheckQuery.invalidCoordinateY(userInput)){
                        consoleWork.print("Неверные данные, координата Y не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(salary)) {
                    if (CheckQuery.invalidSalary(userInput)){
                        consoleWork.print("Неверные данные, заработная плата не должна быть пустой строкой, не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(startDate)) {
                    if (CheckQuery.invalidStartDate(userInput)){
                        consoleWork.print("Неверные данные, дата начала не должна быть пустой строкой, не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(endDate)) {
                    if (CheckQuery.invalidEndDate(userInput)){
                        consoleWork.print("Неверные данные, дата начала не должна быть пустой строкой, не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(status)) {
                    if (CheckQuery.invalidStatus(userInput)){
                        consoleWork.print("Неверные данные, дата начала не должна быть пустой строкой, не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(personWeight)) {
                    if (userInput == null || userInput.trim().isEmpty()){
                        consoleWork.print("Неверные данные, дата deedначала не должна быть пустой строкой, не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(personHairColor)) {
                    if (userInput == null || userInput.trim().isEmpty()){
                        consoleWork.print("Неверные данные, дата нdeeачала не должна быть пустой строкой, не должна быть null");
                        continue;
                    }
                }
                if (entry.getKey().equals(personCountry)) {
                    if (userInput == null || userInput.trim().isEmpty()){
                        consoleWork.print("Неверные данные, дата начала не должна быть пустой строкой, не должна быть null");
                        continue;
                    }
                }

                arguments.add(userInput);
                break;
            }
        }
        return new Query("add", arguments);
    }
}
