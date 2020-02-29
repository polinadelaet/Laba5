package app.query.queryBuilder;
import app.collection.Worker;
import app.console.ConsoleWork;
import app.query.Query;
import app.query.queryCreationException.QueryCreationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddQueryBuilder extends QueryBuilder{

    private final List<String> arguments;
    private final Map<String, String> fields = new HashMap<String,String>(){{
        put("name", "Введите имя: ");
        put("coordinate x", "Введите вещественное число: ");
        put("coordinate y", "Введите целое число: ");
        put("salary", "Введите заработную плату: ");
        put("startDate", "Введите дату начала: ");
        put("endDate", "Введите дату окончания: ");
        put("status", "Выберите статус из " +
                        "HIRED" + System.lineSeparator() +
                        "REGULAR" + System.lineSeparator() +
                        "PROBATION" + System.lineSeparator());
        put("person weight", "Введите вещественное число: ");
        put("person hairColor", "Выберите цвет волос из " +
                        "GREEN" + System.lineSeparator() +
                        "YELLOW" + System.lineSeparator() +
                        "WHITE" + System.lineSeparator() +
                        "BROWN" + System.lineSeparator());
        put("person country", "Введите страну из " +
                        "RUSSIA" + System.lineSeparator() +
                        "INDIA" + System.lineSeparator() +
                        "ITALY" + System.lineSeparator());
    }};
    public AddQueryBuilder(ConsoleWork consoleWork){
       super(consoleWork);
       arguments = new ArrayList<>();
    }

    @Override
    public Query create(String [] subStrings)throws QueryCreationException {
        if(subStrings.length != 1){
            throw new QueryCreationException("Аргументы команды должны записываться с новой строчки");
        }

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            consoleWork.printLine(entry.getValue());

            String userInput;
            while (true) {
                userInput = consoleWork.readLine();
                if (entry.getKey().equals("name")) {
                    if (userInput == null || userInput.isEmpty()) {
                        consoleWork.print("Неверные данные, имя должно быть не пустой строкой, не должно быть null");
                    }
                }
                if (entry.getKey().equals(""))
            }
            arguments.add(userInput);
        }
        return new Query("add", arguments);
    }
}
