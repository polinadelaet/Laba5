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
            if (previousLine == null || previousLine.isEmpty()){
                throw new ScriptException("Неправильный скрипт. Держи червя");
            }
            String[] firstLine = previousLine.split(" +");

            if (firstLine.length != 1 && firstLine.length != 2){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (firstLine.length == 2){
                checkArgument(firstLine);
                arguments.add(firstLine[1]);
            }
        return arguments;
    }
//TODO person убрать из симпл
    private void checkArgument(String[] firstLine) throws ScriptException {
        if (firstLine[0].equals("remove_by_id") && CheckField.invalidId(firstLine[1])){
            throw new ScriptException("Неправильный скрипт.");
        }
        if (firstLine[0].equals("execute_script") && CheckField.invalidName(firstLine[1])){
            throw new ScriptException("Неправильный скрипт.");
        }
        if (firstLine[0].equals("count_by_end_date") && CheckField.invalidEndDate(firstLine[1])){
            throw new ScriptException("Неправильный скрипт.");
        }
        if (firstLine[0].equals("print_field_descending_end_date") && CheckField.invalidEndDate(firstLine[1])){
            throw new ScriptException("Неправильный скрипт.");
        }
    }
}
