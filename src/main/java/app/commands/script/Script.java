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

    //TODO: двойной эндперсант вместо одинарного
    public boolean hasNextLine(){
        return (currentIndex < lines.size()) & (lines.size() != 0);
    }
    public String getNextLine() throws ScriptException {
        try {
            String line = lines.get(currentIndex);
            currentIndex++;
            return line;
        } catch (IndexOutOfBoundsException e){
            //TODO: не обязательно ошибка в этом, может быть еще currentIndex >= lines.size()
            throw new ScriptException("Файл со скриптом пуст.");

        }
    }

    //TODO: даже несмотря на ошибку, currentIndex будет все равно изменяться, что испортит тебе малину
    public String getPreviousLine() throws ScriptException {
        try {
            currentIndex--;
            return lines.get(currentIndex);
        } catch (IndexOutOfBoundsException e){
            throw new ScriptException("Держи червя");

        }

    }
}
