package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.ScriptException;

import java.util.ArrayList;
import java.util.List;

public class CompositeArgumentFormer extends ArgumentFormer {

    //TODO: знака равенства не должно быть
    private List<String> fields = new ArrayList<String>(){{
        add("name");
        add("coordinate_x=");
        add("coordinate_y=");
        add("salary=");
        add("start_date=");
        add("end_date=");
        add("status=");
        add("person_weight=");
        add("person_hair_color=");
        add("person_country=");
    }};
    //TODO: некорректное название, индекс чего???
    private int currentIndex = 0;

    @Override
    public List<String> collectArguments(Script script) {
        try {
            String[] firstLine = script.getPreviousLine().split(" +");

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
                    //TODO: некорректное название переменной
                    String[] field = line.split("=");
                    checkArguments(field);

                    arguments.add(field[1]);
                    line = script.getNextLine();
                }
            }
        } catch (ScriptException e){
            e.getMessage();
        }
        return arguments;
    }


    private boolean checkArguments(String[] field) throws ScriptException {
        if (field[0].equals(fields.get(currentIndex))){
            currentIndex++;
            //TODO: зачем возвращать булево значение, если ты испольщуешь метод как void
            return true;
        } throw new ScriptException("Неправильный скрипт!");

    }
}
