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
        add("person_nationality");
    }};

    private int currentIndexOfLine = 0;

    @Override
    public List<String> collectArguments(Script script) throws ScriptException {

            String previousLine = script.getPreviousLine();
            if (previousLine == null || previousLine.isEmpty()){
                throw new ScriptException("Неправильный скрипт. Держи червя");
            }
            String[] firstLine = previousLine.split(" +");

            //TODO: рефакторинг, нечитаемо
            //TODO: эти if-ы можно скрыть в иерархии ArgumentFormer, то есть сделать два аргумент формера вместо Composite
            if (firstLine[0].equals("filter_by_person")){
                String line = script.getNextLine();
                while (line.contains("=")){
                    String[] subStrings = line.split("=");
                    checkArgumentFilterByPerson(subStrings);

                    currentIndexOfLine++;
                    arguments.add(subStrings[1]);
                    line = script.getNextLine();
                }
            }
            if (firstLine[0].equals("add") | firstLine[0].equals("add_if_max") | firstLine[0].equals("remove_lower")){
                String line = script.getNextLine();
                while (line.contains("=")){
                    String[] subStrings = line.split("=");
                    checkArgument(subStrings);

                    currentIndexOfLine++;
                    arguments.add(subStrings[1]);
                    line = script.getNextLine();
                }
            }
            if ((firstLine[0].equals("update") | firstLine[0].equals("insert_at")) & firstLine.length == 2){
                arguments.add(firstLine[1]);
                String line = script.getNextLine();

                while (line.contains("=")){
                    String[] subStrings = line.split("=");
                    checkArgument(subStrings);

                    currentIndexOfLine++;
                    arguments.add(subStrings[1]);
                    line = script.getNextLine();
                }
            }
        return arguments;
    }

    private void checkArgumentFilterByPerson(String[] field) throws ScriptException {
        if (field[0].equals(fields.get(currentIndexOfLine+7))){
            if (fields.get(currentIndexOfLine+7).equals("person_weight") && CheckField.invalidPersonWeight(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine+7).equals("person_hair_color") && CheckField.invalidPersonHairColor(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine+7).equals("person_nationality") && CheckField.invalidPersonNationality(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
        }
    }
    private void checkArgument(String[] field) throws ScriptException {
        if (field[0].equals(fields.get(currentIndexOfLine))){
            if (fields.get(currentIndexOfLine).equals("name") && CheckField.invalidName(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("coordinate_x") && CheckField.invalidCoordinateX(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("coordinate_y") && CheckField.invalidCoordinateY(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("salary") && CheckField.invalidSalary(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("end_date") && CheckField.invalidEndDate(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("status") && CheckField.invalidStatus(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("person_weight") && CheckField.invalidPersonWeight(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("person_hair_color") && CheckField.invalidPersonHairColor(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine).equals("person_nationality") && CheckField.invalidPersonNationality(field[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
        }
    }
}
