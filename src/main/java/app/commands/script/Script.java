package app.commands.script;

import java.util.List;

public final class Script {

    private final List<String> lines;
    private int currentIndex;

    public Script(List<String> lines) {
        this.lines = lines;
        this.currentIndex = 0;
    }

    public List<String> getLines() {
        return lines;
    }

    public String getNextLine(){
        String line = lines.get(currentIndex);
        currentIndex++;
        return line;
    }

    public boolean hasNextLine(){
        if (currentIndex < lines.size()) {
            return true;
        } return false;
    }
}
