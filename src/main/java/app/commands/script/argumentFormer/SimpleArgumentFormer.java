package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.ScriptException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleArgumentFormer extends ArgumentFormer{


    @Override
    public List<String> collectArguments(Script script) {
        List<String> arguments = new ArrayList<>();
        try {
            String firstLine = script.getPreviousLine();
            String [] subStrings  = firstLine.split(" +");
            //TODO: бредовое условие
            if (subStrings.length != 1 & subStrings.length < 3){
                arguments.add(subStrings[1]);
            } throw new ScriptException("Неправильный скрипт.");
        }catch (ScriptException e){
            //TODO: это че за хуйня??? Ты кидаешь ошибку в трай и тут же ловишь в катч???
            e.getMessage();
        }
        return arguments;
    }

}
