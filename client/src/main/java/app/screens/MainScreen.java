package screens;

import app.Console;
import app.Viewer;
import controller.Controller;
import manager.LogManager;
import response.Response;
import response.Status;

public final class MainScreen extends ConsoleScreen {
    private static final LogManager LOG_MANAGER = LogManager.createDefault(MainScreen.class);


    public MainScreen(Console console, Viewer viewer, Controller controller) {
        super(console, viewer, controller);
    }


    @Override
    protected void showScreenDescription() {
        console.writeLine("Now you can work with collection. For details, please, enter \"help\" command.");
    }


    protected void analyseResponse(Response response) {
        LOG_MANAGER.debug("Server answered: " + response);

        if (response.getStatus().equals(Status.BAD_REQUEST)) {
            console.writeLine(viewer.showBadRequestErrorMessage());
            console.writeLine(viewer.showPrefixServerAnswer() + response.getAnswer());
            console.writeLine(viewer.showOfferToRepeatInput());
        }

        if (response.getStatus().equals(Status.SUCCESSFULLY)) {
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
