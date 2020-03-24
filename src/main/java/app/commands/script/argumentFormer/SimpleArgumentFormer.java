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
        }catch (ScriptException e){
            //TODO: это че за хуйня??? Ты кидаешь ошибку в трай и тут же ловишь в катч???

        }
        return arguments;
    }
}
