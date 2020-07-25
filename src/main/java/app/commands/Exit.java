package app.commands;


import response.Response;
import response.Status;

import java.util.List;

public final class Exit extends Command {
    public Exit(List<String> inputArguments) {
        super(inputArguments);
    }

    @Override
    public Response execute() {
        return new Response(Status.TIME_TO_EXIT, "Программа завершена.");
    }
}
