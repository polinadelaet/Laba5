package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.ScriptException;

import java.util.ArrayList;
import java.util.List;

public class SimpleArgumentFormer extends ArgumentFormer{
    @Override
    public List<String> collectArguments(Script script) {
        List<String> arguments = new ArrayList<>();
        try {
            String firstLine = script.getNextLine();
            String [] subStrings  = firstLine.split(" +");
            if (subStrings.length != 1){
                arguments.add(subStrings[1]);
            }
        } catch (ScriptException e){
            e.getMessage();
        }
        return arguments;
    }
}
