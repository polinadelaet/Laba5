package app.commands;

import response.Response;
import response.Status;

import java.util.HashMap;
import java.util.List;

public final class Exit extends Command {
    public Exit(HashMap<String, String> inputArguments) {
        super(inputArguments);
    }

    @Override
    public Response execute() {
        return new Response(Status.OK, "Программа завершена.");
    }
}
