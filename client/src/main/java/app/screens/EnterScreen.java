package screens;

import app.Console;
import app.Viewer;
import controller.Controller;
import response.Response;
import response.Status;

public final class EnterScreen extends ConsoleScreen {
    public EnterScreen(Console console,
                       Viewer viewer,
                       Controller controller) {
        super(console, viewer, controller);
    }

    @Override
    protected void showScreenDescription() {
        console.writeLine(viewer.showGreetingMessage());
    }

    @Override
    protected void analyseResponse(Response response) {
        if (response.getStatus().equals(Status.SUCCESSFULLY)) {
            String[] subStrings = response.getAnswer().split(" +");

            screenContext.add("login", subStrings[0]);
            screenContext.add("password", subStrings[1]);

            isActive = false;
            screenContext.getRouter().go("main");
        }

        if (response.getStatus().equals(Status.BAD_REQUEST)) {
            console.writeLine(viewer.showBadRequestErrorMessage());
            console.writeLine(response.getAnswer());
            console.writeLine(viewer.showOfferToRepeatInput());
        }

        if (response.getStatus().equals(Status.INTERNAL_ERROR)) {
            console.writeLine(viewer.showInternalServerErrorMessage());
            console.writeLine(viewer.showOfferToRepeatInput());
        }

        console.writeLine();
    }
}
