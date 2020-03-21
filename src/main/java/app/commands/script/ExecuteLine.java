package app.commands.script;

import java.util.Iterator;
import java.util.List;

public class ExecuteLine {


    public void execute(Script script) throws FileCreationException {
        while (script.hasNextLine()){
            script.getNextLine();
        }
    }
}
