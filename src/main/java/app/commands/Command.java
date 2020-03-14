package app.commands;

import app.response.Response;

import java.util.List;

public abstract class Command {

    protected final List<String> inputArguments;

    public Command(List<String> inputArguments) {
        this.inputArguments = inputArguments;
    }

    public abstract Response execute();
}
