package app.commands.script;

import java.util.Iterator;
import java.util.List;

public class ExecuteLine {

    private List<String> lines;
    private String filePath;

    public ExecuteLine(List<String> lines) {
        this.lines = lines;
    }

    public void execute(Script script) throws FileCreationException {
        while (script.hasNextLine()){
            script.getNextLine();
        }
    }
}
