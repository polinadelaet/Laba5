package app.commands.script;

import java.io.*;
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

            return new Script(processReading(inputStreamReader));
        } catch (java.io.IOException e){
            throw new FileCreationException("Такого файла не существует.");
        }
    }

    private List<String> processReading(InputStreamReader inputStreamReader) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder lineBuilder = new StringBuilder();

        int result = inputStreamReader.read();

        while (!isEoF(result)){
            char symbol = (char) result;

            if (isEndOfLine(symbol)) {
                addLine(lines, lineBuilder);
                lineBuilder = new StringBuilder();

                result = inputStreamReader.read();

                continue;
            }

            lineBuilder.append(symbol);
            result = inputStreamReader.read();
        }

        return lines;
    }

    private void addLine(List<String> lines, StringBuilder lineBuilder) {
        String line = lineBuilder.toString();

        if (extraKaretReturn(line)) {
            line = line.replace("\r", "");
        }

        lines.add(line);
    }

    private boolean extraKaretReturn(String line) {
        return line.endsWith("\r");
    }

    private boolean isEndOfLine(char symbol) {
        return symbol == '\n';
    }

    private boolean isEoF(int result) {
        return result == -1;
    }
}
