package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.scriptException.ScriptException;

import java.util.ArrayList;
import java.util.List;

public class SimpleArgumentFormer extends ArgumentFormer{


    @Override
    public List<String> collectArguments(Script script) throws ScriptException {
        List<String> arguments = new ArrayList<>();
            String previousLine = script.getPreviousLine();
            if (previousLine == null){
                throw new ScriptException("Неправильный скрипт. Держи червя");
            }
            String[] firstLine = previousLine.split(" +");

            if (firstLine.length != 1 || firstLine.length != 2){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (firstLine.length == 2){
                arguments.add(firstLine[1]);
            }
        return arguments;
    }
}
