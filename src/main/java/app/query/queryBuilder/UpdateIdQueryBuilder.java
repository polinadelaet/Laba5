package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;

import java.util.Map;


public class UpdateIdQueryBuilder extends CompositeQueryBuilder{

    public UpdateIdQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    protected int getSubStringsLegalLength() {
        return 2;
    }
    @Override
    public Query processCreation(String [] subStrings) {

        String id = subStrings[1];

        // 1)Вывести список всех полей
        // 2) нужно считать конкретное поле которое я буду обновлять
        // 3) считать новое значение поля
        // 4) предусмотреть выход из команды создания(команда стоп(stop))

        arguments.add(id);
        for (Map.Entry<String, String> entry : fields.entrySet()){
            consoleWork.printLine(entry.getKey());
        }

        while (true) {
            String targetField = readTargetField();

            if (targetField.equals("exit")) {
                break;
            }

            boolean exitWasWritten = readFieldValueIntoArguments(targetField);

            if (exitWasWritten) {
                break;
            }
        }

        return new Query("update", arguments);
    }
}
