package app.commands.script;

import app.commands.script.scriptException.ScriptException;

import java.util.List;

public final class Script {

    private final List<String> lines;
    private int currentIndex;

    public Script(List<String> lines) {
        this.lines = lines;
        this.currentIndex = 0;
    }

    public void increaseIndex(){
        currentIndex++;
    }

    public void decreaseIndex(){
        currentIndex--;
    }

    public boolean hasNextLine(){
        return ((currentIndex < lines.size()) && (lines.size() != 0));
    }
    public String getNextLine() throws ScriptException {
        try {
            String line = lines.get(currentIndex);
            currentIndex++;
            return line;
        } catch (IndexOutOfBoundsException e){
            throw new ScriptException(e);

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
            throw new ScriptException(e);
        }
    }

    @Override
    public int hashCode() {
        int result = lines != null ? lines.hashCode() : 0;
        result = 31 * result + currentIndex;
        return result;
    }

    public List<String> getLines() {
        return lines;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
