package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.scriptException.ScriptException;

import java.util.ArrayList;
import java.util.List;

public class CompositeArgumentFormer extends ArgumentFormer {

    private List<String> fields = new ArrayList<String>(){{
        add("name");
        add("coordinate_x");
        add("coordinate_y");
        add("salary");
        add("start_date");
        add("end_date");
        add("status");
        add("person_weight");
        add("person_hair_color");
        add("person_country");
    }};

    private int currentIndexOfLine = 0;

    @Override
    public List<String> collectArguments(Script script) throws ScriptException {

            String previousLine = script.getPreviousLine();
            if (previousLine == null){
                throw new ScriptException("Неправильный скрипт. Держи червя");
            }
            String[] firstLine = previousLine.split(" +");

            //TODO: рефакторинг, нечитаемо
            //TODO: эти if-ы можно скрыть в иерархии ArgumentFormer, то есть сделать два аргумент формера вместо Composite
            if (firstLine[0].equals("add") | firstLine[0].equals("add_if_max") | firstLine[0].equals("remove_lower")){
                String line = script.getNextLine();
                while (line.contains("=")){
                    String[] field = line.split("=");
                    arguments.add(field[1]);
                    line = script.getNextLine();
                }
            }
            if ((firstLine[0].equals("update") | firstLine[0].equals("insert_at")) & firstLine.length == 2){
                arguments.add(firstLine[1]);
                String line = script.getNextLine();

                while (line.contains("=")){
                    String[] subStrings = line.split("=");
                    checkArguments(subStrings);

                    arguments.add(subStrings[1]);
                    line = script.getNextLine();
                }
            }
        return arguments;
    }


    private void checkArguments(String[] field) throws ScriptException {
        if (field[0].equals(fields.get(currentIndexOfLine))){
            currentIndexOfLine++;
        } throw new ScriptException("Неправильный скрипт!");
    }
}
