package app.commands.script;

import java.util.List;

//TODO а где методы скрипта????
public final class Script {

    private final List<String> lines;
    private int currentIndex;

    public Script(List<String> lines) {
        this.lines = lines;
        //TODO: а если size == 0?
        this.currentIndex = 0;
    }

    //TODO: нельзя получить сразу все строчки - получаем по одной
    public List<String> getLines() {
        return lines;
    }

    public String getNextLine(){
        //TODO: а если size <= currentIndex?
        String line = lines.get(currentIndex);
        currentIndex++;
        return line;
    }

    public boolean hasNextLine(){
        //TODO: упростить
        if (currentIndex < lines.size()) {
            return true;
        } return false;
    }
}
