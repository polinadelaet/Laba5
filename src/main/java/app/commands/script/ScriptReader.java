package app.commands.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ScriptReader {

    public Script read(String filePath) throws FileCreationException {

        try (Scanner scanner = new Scanner(new File(filePath))){
            List<String> lines = new ArrayList<>();

            while (scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }
            return new Script(lines);
        } catch (FileNotFoundException e){
            throw new FileCreationException("Такого файла не существует.");
        }


    }
}
