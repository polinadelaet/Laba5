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

           /*
        try (Scanner scanner = new Scanner(new File(filePath))){
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }
            return new Script(lines);
*/

            return new Script(processReading(inputStreamReader));
        } catch (java.io.IOException e){
            throw new FileCreationException("Такого файла не существует или нет доступа к файлу.");
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

        if(lineBuilder.length() != 0){
            addLine(lines, lineBuilder);
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
