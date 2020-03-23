package app.commands.script;

import app.response.Response;
import app.response.Status;

import java.util.List;

public final class Script {

    private final List<String> lines;
    private int currentIndex;

    public Script(List<String> lines) {
        this.lines = lines;
        this.currentIndex = 0;
    }

    public boolean hasNextLine(){
        return currentIndex < lines.size() & lines.size() != 0;
    }
    public String getNextLine() throws ScriptException {
        try {
            return lines.get(currentIndex);
        } catch (IndexOutOfBoundsException e){
            throw new ScriptException("Файл со скриптом пуст.");

        }
    }
}
