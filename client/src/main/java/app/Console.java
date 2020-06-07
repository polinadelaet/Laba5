package app;

import adapter.LoggerAdapter;
import app.Exceptions.ConsoleException;
import app.controller.components.serviceMediator.Service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Console implements Service {
    private static final LoggerAdapter LOGGER_ADAPTER = LoggerAdapter.createDefault(Console.class.getSimpleName());

    private final BufferedReader reader;
    private final BufferedOutputStream writer;

    public Console(InputStream inputStream,
                   OutputStream outputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        writer = new BufferedOutputStream(outputStream);
    }

    public void writeLine() throws ConsoleException {
        write(System.lineSeparator());
    }

    public void writeLine(String string) throws ConsoleException {
        String output = string + System.lineSeparator();

        write(output);
    }

    public void write(String string) throws ConsoleException {
        try {
            byte[] buffer = string.getBytes();
            writer.write(buffer, 0, buffer.length);
            writer.flush();
        } catch (IOException exception) {
            LOGGER_ADAPTER.fatal("An error occurred while writing in app.Console.");
            throw new ConsoleException(exception);
        }
    }

    /**
     * Reads next line from BufferedReader.
     * Note: if user writes empty line then this method return null.
     *
     * @return user input. If it's empty then return null.
     * @throws ConsoleException
     */
    public String readLine() throws ConsoleException {
        write(System.lineSeparator() + ">");

        try {
            String userInput = reader.readLine();

            String temp = userInput;
            LOGGER_ADAPTER.info("User entered: " + temp);

            if (userInput != null) {
                userInput = userInput.trim();

                Pattern pattern = Pattern.compile("^[ +]");
                Matcher matcher = pattern.matcher(userInput);

                if (userInput.isEmpty() || matcher.find()) {
                    userInput = null;
                }
            }


            return userInput;
        } catch (IOException exception) {
            LOGGER_ADAPTER.fatal("An error occurred while reading in app.Console.");
            throw new ConsoleException(exception);
        }
    }
}
