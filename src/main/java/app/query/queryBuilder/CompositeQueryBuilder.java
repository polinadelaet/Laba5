package app.query.queryBuilder;

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
        put(name, "Enter name: ");
        put(coordinateX, "Enter double number(coordinate x): ");
        put(coordinateY, "Enter integer(coordinate y): ");
        put(salary, "Enter salary: ");
        put(startDate, "Enter the start date like this: 2007-12-03T10:15:30+01:00[Europe/Paris]");
        put(endDate, "Enter end date in format YEAR-MONTH-DAY: ");
        put(status, "Select a status from the proposed:" + System.lineSeparator() +
                "HIRED" + System.lineSeparator() +
                "REGULAR" + System.lineSeparator() +
                "PROBATION" + System.lineSeparator());
        put(personWeight, "Enter double number(weight): ");
        put(personHairColor, "Select hair color from the proposed:" + System.lineSeparator() +
                "GREEN" + System.lineSeparator() +
                "YELLOW" + System.lineSeparator() +
                "WHITE" + System.lineSeparator() +
                "BROWN" + System.lineSeparator());
        put(personCountry, "Select country from the proposed:" + System.lineSeparator() +
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
                    consoleWork.printLine("Invalid data, the name mustn't be an empty string, it must be not be null.");
                    continue;
                }
            }
            if (targetField.equals(coordinateX)) {
                if (CheckQuery.invalidCoordinateX(userInput)){
                    consoleWork.printLine("Invalid data, coordinate X should be double number, it must be not be null.");
                    continue;
                }
            }
            if (targetField.equals(coordinateY)) {
                if (CheckQuery.invalidCoordinateY(userInput)){
                    consoleWork.printLine("Invalid data, coordinate Y should be integer, it must be not be null.");
                    continue;
                }
            }
            if (targetField.equals(salary)) {
                if (CheckQuery.invalidSalary(userInput)){
                    consoleWork.printLine("Invalid data, salary should be greater than zero.");
                    continue;
                }
            }
            if (targetField.equals(startDate)) {
                if (CheckQuery.invalidStartDate(userInput)){
                    consoleWork.printLine("Invalid data, start date mustn't be an empty string, it must be not be null, write in date format.");
                    continue;
                }
            }
            if (targetField.equals(endDate)) {
                if (CheckQuery.invalidEndDate(userInput)){
                    consoleWork.printLine("Invalid data, end date mustn't be an empty string, write in date format.");
                    continue;
                }
            }
            if (targetField.equals(status)) {
                if (CheckQuery.invalidStatus(userInput)){
                    consoleWork.printLine("Invalid data, you should select from the proposed constants, status mustn't be null.");
                    continue;
                }
            }
            if (targetField.equals(personWeight)) {
                if (CheckQuery.invalidPersonWeight(userInput)){
                    consoleWork.printLine("Invalid data, weight should be double number greater than zero.");
                    continue;
                }
            }
            if (targetField.equals(personHairColor)) {
                if (CheckQuery.invalidPersonHairColor(userInput)){
                    consoleWork.printLine("Invalid data, you should select hair color from the proposed constants, mustn't be null.");
                    continue;
                }
            }
            if (targetField.equals(personCountry)) {
                if (CheckQuery.invalidPersonNationality(userInput)){
                    consoleWork.printLine("Invalid data, you should select country from the proposed constants, mustn't be null.");
                    continue;
                }
            }
            arguments.add(userInput);
            return false;
        }
    }

}
