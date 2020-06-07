package app.screens;

import adapter.LoggerAdapter;
import app.Console;
import app.Viewer;
import app.controller.Controller;
import response.Response;
import response.Status;

public final class MainScreen extends ConsoleScreen {
    private static final LoggerAdapter LOGGER_ADAPTER = LoggerAdapter.createDefault(MainScreen.class.getSimpleName());


    public MainScreen(Console console, Viewer viewer, Controller controller) {
        super(console, viewer, controller);
    }


    @Override
    protected void showScreenDescription() {
        console.writeLine("Now you can work with collection. For details, please, enter \"help\" command.");
    }


    protected void analyseResponse(Response response) {
        LOGGER_ADAPTER.debug("Server answered: " + response);

        if (response.getStatus().equals(Status.BAD_REQUEST)) {
            console.writeLine(viewer.showBadRequestErrorMessage());
            console.writeLine(viewer.showPrefixServerAnswer() + response.getAnswer());
            console.writeLine(viewer.showOfferToRepeatInput());
        }

        if (response.getStatus().equals(Status.OK)) {
            console.writeLine(response.getAnswer());
        }

        if (response.getStatus().equals(Status.INTERNAL_ERROR)) {
            console.writeLine(viewer.showInternalServerErrorMessage());
            console.writeLine(viewer.showOfferToRepeatInput());
        }

        if (response.getStatus().equals(Status.PRECONDITION_FAILED)) {
            console.writeLine(viewer.showUnsuccessfulCommandMessage());
            console.writeLine(viewer.showPrefixServerAnswer() + response.getAnswer());
            console.writeLine(viewer.showOfferToRepeatInput());
        }

        if (response.getStatus().equals(Status.GO_BACK)) {
            screenContext.remove("login");
            screenContext.remove("password");
            screenContext.getRouter().go("enter");
        }

        console.writeLine();
    }
}
