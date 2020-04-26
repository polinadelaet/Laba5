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

            arguments.clear();
            String previousLine = script.getPreviousLine();

            if (previousLine == null || previousLine.isEmpty()){
                throw new ScriptException("Неправильный скрипт. Держи червя");
            }
            String[] firstLine = previousLine.split(" +");
            //TODO: рефакторинг, нечитаемо
            //TODO: эти if-ы можно скрыть в иерархии ArgumentFormer, то есть сделать два аргумент формера вместо Composite
            if (firstLine[0].equals("filter_by_person")){
                script.increaseIndex();
                String line = script.getNextLine();

                for (int i = 7 ; i<10; i++){
                    System.out.println(line);
                    String[] subStrings = line.split("=");
                    checkArgument(subStrings, i);
                    arguments.add(subStrings[1]);
                    if (script.hasNextLine()) {
                        line = script.getNextLine();
                    }
                }
                script.decreaseIndex();
            }
            if (firstLine[0].equals("add") | firstLine[0].equals("add_if_max") | firstLine[0].equals("remove_lower")){
                script.increaseIndex();
                String line = script.getNextLine();
                for (int i = 0 ; i<10; i++){
                    String[] subStrings = line.split("=");
                    checkArgument(subStrings, i);
                    arguments.add(subStrings[1]);
                    if (script.hasNextLine()) {
                        line = script.getNextLine();
                    }
                }
                script.decreaseIndex();
            }
            if ((firstLine[0].equals("update") | firstLine[0].equals("insert_at")) & firstLine.length == 2){
                arguments.add(firstLine[1]);
                script.increaseIndex();
                String line = script.getNextLine();

                for (int i = 0 ; i<10; i++){
                    String[] subStrings = line.split("=");
                    checkArgument(subStrings, i);
                    arguments.add(subStrings[1]);
                    if (script.hasNextLine()) {
                        line = script.getNextLine();
                    }
                }
                script.decreaseIndex();
            }

        return arguments;
    }

    private void checkArgumentFilterByPerson(String[] subStrings) throws ScriptException {
        if (subStrings[0].equals(fields.get(currentIndexOfLine+7))){
            if (fields.get(currentIndexOfLine+7).equals("person_weight") && CheckField.invalidPersonWeight(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine+7).equals("person_hair_color") && CheckField.invalidPersonHairColor(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(currentIndexOfLine+7).equals("person_nationality") && CheckField.invalidPersonNationality(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
        }
    }

    private void checkArgument(String[] subStrings, int i) throws ScriptException {
        if (subStrings[0].equals(fields.get(i))){

            if (fields.get(i).equals("name") && CheckField.invalidName(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("coordinate_x") && CheckField.invalidCoordinateX(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("coordinate_y") && CheckField.invalidCoordinateY(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("salary") && CheckField.invalidSalary(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("start_date") && CheckField.invalidStartDate(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("end_date") && CheckField.invalidEndDate(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("status") && CheckField.invalidStatus(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("person_weight") && CheckField.invalidPersonWeight(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("person_hair_color") && CheckField.invalidPersonHairColor(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }
            if (fields.get(i).equals("person_nationality") && CheckField.invalidPersonNationality(subStrings[1]) == true){
                throw new ScriptException("Неправильный скрипт.");
            }

        }
    }
}
