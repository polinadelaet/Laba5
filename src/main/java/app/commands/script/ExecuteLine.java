package app.commands.script;

import java.util.Iterator;
import java.util.List;

//TODO: выполнить одну только строку? Или весь скрипт? Подкерректируй название
public class ExecuteLine {


    public void execute(Script script) throws FileCreationException {
        while (script.hasNextLine()){
            script.getNextLine();
            //TODO: а дальше-то че
        }
    }
}
