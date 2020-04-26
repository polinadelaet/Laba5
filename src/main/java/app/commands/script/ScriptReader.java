package app.commands.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ScriptReader {

    public Script read(String filePath) throws FileCreationException {
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)){

            /*List<String> lines = new ArrayList<>();
            int symbol;
            while ((symbol = inputStreamReader.read()) != -1){
                String line = "";

                while ((char) symbol != '\n' ){
                    line += (char) symbol;
                    if ((symbol = inputStreamReader.read()) == -1){
                        break;
                    };
                }
                lines.add(line);
            }*/ //ДЛЯ ЛИНУКСА

            //***ДЛЯ WINDOWS********************************
            List<String> lines = new ArrayList<>();
            int symbol;
            while ((symbol = inputStreamReader.read()) != -1){
                String line = "";
                while (((char) symbol != '\r') & ((char) symbol != '\n') ){
                    line += (char) symbol;
                    if ((symbol = inputStreamReader.read()) == -1){
                        break;
                    };
                }
                inputStreamReader.read();
                lines.add(line);
            }
            //System.out.println(lines.toString());;

            /*while (scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }*/

            return new Script(lines);
        } catch (java.io.IOException e){
            throw new FileCreationException("Такого файла не существует.");
        }


    }
}
