package app.queryBuilder.queryBuilder;

import app.console.ConsoleWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CompositeQueryBuilder extends QueryBuilder {
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
    protected final List<String> nameOfField = new ArrayList<String>(){{
        add(name);
        add(coordinateX);
        add(coordinateY);
        add(salary);
        add(startDate);
        add(endDate);
        add(status);
        add(personWeight);
        add(personHairColor);
        add(personCountry);
    }};
    protected final Map<String, String> fields = new HashMap<String, String>(){{
        put(name, "Введите имя: ");
        put(coordinateX, "Введите вещественное число(координату х): ");
        put(coordinateY, "Введите целое число(координату y): ");
        put(salary, "Введите заработную плату: ");
        put(startDate, "Введите дату начала в формате 2007-12-03T10:15:30+01:00[Europe/Paris]: ");
        put(endDate, "Введите дату окончания в формате ГОД-МЕСЯЦ-ДЕНЬ: ");
        put(status, "Выберите статус из предложенных:" + System.lineSeparator() +
                "HIRED" + System.lineSeparator() +
                "REGULAR" + System.lineSeparator() +
                "PROBATION" + System.lineSeparator());
        put(personWeight, "Введите вещественное число(вес): ");
        put(personHairColor, "Выберите цвет волос из предложенных:" + System.lineSeparator() +
                "GREEN" + System.lineSeparator() +
                "YELLOW" + System.lineSeparator() +
                "WHITE" + System.lineSeparator() +
                "BROWN" + System.lineSeparator());
        put(personCountry, "Введите страну из предложенных:" + System.lineSeparator() +
                "RUSSIA" + System.lineSeparator() +
                "INDIA" + System.lineSeparator() +
                "ITALY" + System.lineSeparator());
    }};


    public CompositeQueryBuilder(ConsoleWork consoleWork) {
        super(consoleWork);
    }

    /**
     * Если был введен выход, вернет true, иначе - false.
     */
    protected boolean readFieldValueIntoArguments(String targetField){
        while (true) {

            String userInput = consoleWork.readLine();


            if (targetField.equals(name)) {
                if (CheckQuery.invalidName(userInput)) {
                    consoleWork.printLine("Неверные данные, имя должно быть не пустой строкой, не должно быть null.");
                    continue;
                }
            }
            if (targetField.equals(coordinateX)) {
                if (CheckQuery.invalidCoordinateX(userInput)){
                    consoleWork.printLine("Неверные данные, координата Х должна быть дробным числом, не должна быть null.");
                    continue;
                }
            }
            if (targetField.equals(coordinateY)) {
                if (CheckQuery.invalidCoordinateY(userInput)){
                    consoleWork.printLine("Неверные данные, координата Y должна быть целым числом, не должна быть null.");
                    continue;
                }
            }
            if (targetField.equals(salary)) {
                if (CheckQuery.invalidSalary(userInput)){
                    consoleWork.printLine("Неверные данные, заработная плата должна быть больше нуля.");
                    continue;
                }
            }
            if (targetField.equals(startDate)) {
                if (CheckQuery.invalidStartDate(userInput)){
                    consoleWork.printLine("Неверные данные, дата начала не должна быть пустой строкой, не должна быть null, ее нужно записать в формате даты.");
                    continue;
                }
            }
            if (targetField.equals(endDate)) {
                if (CheckQuery.invalidEndDate(userInput)){
                    consoleWork.printLine("Неверные данные, дата окончания не должна быть пустой строкой, дату окончания нужно записать в формате даты.");
                    continue;
                }
            }
            if (targetField.equals(status)) {
                if (CheckQuery.invalidStatus(userInput)){
                    consoleWork.printLine("Неверные данные, вам нужно выбрать из представленных констант, status не может быть null.");
                    continue;
                }
            }
            if (targetField.equals(personWeight)) {
                if (CheckQuery.invalidPersonWeight(userInput)){
                    consoleWork.printLine("Неверные данные, вес должен быть дробным числом больше 0.");
                    continue;
                }
            }
            if (targetField.equals(personHairColor)) {
                if (CheckQuery.invalidPersonHairColor(userInput)){
                    consoleWork.printLine("Неверные данные, цвет волос нужно выбрать из представленных констант не должна быть null");
                    continue;
                }
            }
            if (targetField.equals(personCountry)) {
                if (CheckQuery.invalidPersonNationality(userInput)){
                    consoleWork.printLine("Неверные данные, дата начала не должна быть пустой строкой, не должна быть null");
                    continue;
                }
            }
            arguments.put(targetField, userInput);
            //arguments.add(userInput);
            return false;
        }
    }

}
