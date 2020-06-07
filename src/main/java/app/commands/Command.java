package app.commands;

import response.Response;

import java.util.HashMap;
import java.util.List;

public abstract class Command {

    //protected final List<String> inputArguments;
    protected final HashMap<String, String> inputArguments;

    public Command(HashMap<String, String> inputArguments) {
        this.inputArguments = inputArguments;
    }

    public abstract Response execute();
}
