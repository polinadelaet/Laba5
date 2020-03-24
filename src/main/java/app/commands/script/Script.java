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
        return (currentIndex < lines.size()) && (lines.size() != 0);
    }
    public String getNextLine() throws ScriptException {
        try {
            String line = lines.get(currentIndex);
            currentIndex++;
            return line;
        } catch (IndexOutOfBoundsException e){
            throw new ScriptException("Неправильный скрипт.");

        }
    }

    public String getPreviousLine() throws ScriptException {
        try {
            if (currentIndex == 0){
                return null;
            }
            String previousLine = lines.get(currentIndex - 1);
            currentIndex--;
            return previousLine;
        } catch (IndexOutOfBoundsException e){
            throw new ScriptException("Неправильный скрипт.");
        }
    }

    @Override
    public int hashCode() {
        int result = lines != null ? lines.hashCode() : 0;
        result = 31 * result + currentIndex;
        return result;
    }
}
