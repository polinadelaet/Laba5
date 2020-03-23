package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.ScriptException;

import java.util.List;

public class ComplexArgumentFormer extends ArgumentFormer {

    @Override
    public List<String> collectArguments(Script script) {
        try {
            String[] firstLine = script.getNextLine().split(" +");
            if (firstLine[0].equals("add") | firstLine[0].equals("add_if_max") | firstLine[0].equals("remove_lower")){
                String line = script.getNextLine();
                while (line.contains("=")){
                    String[] field = line.split("=");
                    arguments.add(field[1]);
                    line = script.getNextLine();
                }
            }
            if (firstLine[0].equals("update") | firstLine[0].equals("insert_at")){
                arguments.add(firstLine[1]);
                String line = script.getNextLine();
                while (line.contains("=")){
                    String[] field = script.getNextLine().split("=");
                    arguments.add(field[1]);
                    line = script.getNextLine();
                }
            }
        } catch (ScriptException e){
            e.getMessage();
        }
        return arguments;
    }
}
