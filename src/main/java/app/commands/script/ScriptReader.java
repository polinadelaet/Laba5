package app.commands.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public final class ScriptReader {

    private List<String> lines;

    public ScriptReader(List<String> lines) {
        this.lines = lines;
    }

    public Script read(String filePath) throws FileCreationException {

        try (Scanner scanner = new Scanner(new File(filePath))){
            while (scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }
            return new Script(lines);
        } catch (FileNotFoundException e){
            throw new FileCreationException("Такого файла не существует.");
        }
    }
}
