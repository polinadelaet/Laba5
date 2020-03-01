package app.query.queryBuilder;

import app.console.ConsoleWork;
import app.query.Query;
import app.query.queryCreationException.QueryCreationException;

import java.util.ArrayList;

public class UpdateIdQueryBuilder extends QueryBuilder{

    public UpdateIdQueryBuilder(ConsoleWork consoleWork){
        super(consoleWork);
    }

    @Override
    public Query processCreation(String [] subStrings) throws QueryCreationException {
        if (subStrings.length != 2) {
            throw new QueryCreationException("Вам нужно ввести ПОКА ЧТО только один аргумент");
        }
        //TODO юзнуть как-то AddQueryBuilder
        //TODO засунуть в return не new ArrayList, а arguments
        //TODO чета сделать не используя адд(предлагать пользователю что вводить)
        //TODO 1)Вывести список всех полей 2) нужно считать конкретное поле которое я буду обновлять  3) считать новое значение поля 4) предусмотреть выход из команды создания(команда стоп(stop))
        //1) тупо переменные ARRRRRRRRRRRRRRRRRRRRRRAAAAAAAYLIST
        //return new Query("updateId", addQueryBuilder.create(subStrings).getArguments());
    }
}
