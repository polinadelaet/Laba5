package app;

import app.controller.components.serviceMediator.Service;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is responsible for displaying results of commands in app.console.
 */
public final class Viewer implements Service {
    private final String GREETINGS_LINE_1;
    private final String GREETINGS_LINE_2;
    private final String INTERNAL_CLIENT_ERROR_MESSAGE;
    private final String INTERNAL_SERVER_ERROR_MESSAGE;
    private final String CLIENT_CANNOT_CONNECT_TO_SERVER_ERROR_MESSAGE;
    private final String LOST_CONNECTION_ERROR_MESSAGE;
    private final String GET_WRONG_MESSAGE_ERROR_MESSAGE;
    private final String NOT_YET_CONNECTED_ERROR_MESSAGE;
    private final String CONNECTED_SUCCESSFULLY_MESSAGE;
    private final String BAD_REQUEST_ERROR_MESSAGE;
    private final String NULL_ENTER_INPUT;
    private final String PREFIX_SERVER_ANSWER;
    private final String UNSUCCESSFULLY_COMMAND_MESSAGE;
    private final String NO_SUCH_COMMAND_ERROR_MESSAGE;
    private final String OFFER_TO_REPEAT_INPUT;
    private final String GOOD_BYE_LINE_1;
    private final String GOOD_BYE_LINE_2;
    private final String ERROR_PREFIX;


    public Viewer() {
        ResourceBundle resourceBundle
                = ResourceBundle.getBundle("localizedStrings.app.Viewer", Locale.getDefault());
        GREETINGS_LINE_1 = resourceBundle.getString("standardMessages.greetings.line1");
        GREETINGS_LINE_2 = resourceBundle.getString("standardMessages.greetings.line2");
        INTERNAL_SERVER_ERROR_MESSAGE = resourceBundle.getString("exceptionMessages.internalServerError");
        INTERNAL_CLIENT_ERROR_MESSAGE = resourceBundle.getString("exceptionMessages.internalClientError");
        CLIENT_CANNOT_CONNECT_TO_SERVER_ERROR_MESSAGE =
                resourceBundle.getString("exceptionMessages.cannotConnectToServer");
        LOST_CONNECTION_ERROR_MESSAGE =
                resourceBundle.getString("exceptionMessages.lostConnection");
        GET_WRONG_MESSAGE_ERROR_MESSAGE =
                resourceBundle.getString("exceptionMessages.getWrongMessage");
        NOT_YET_CONNECTED_ERROR_MESSAGE =
                resourceBundle.getString("exceptionMessages.notYetConnected");
        BAD_REQUEST_ERROR_MESSAGE = resourceBundle.getString("exceptionMessages.badRequest");
        NO_SUCH_COMMAND_ERROR_MESSAGE = resourceBundle.getString("exceptionMessages.noSuchCommand");
        ERROR_PREFIX = resourceBundle.getString("exceptionMessages.errorPrefix");
        NULL_ENTER_INPUT = resourceBundle.getString("standardMessages.nullEnterInput");
        PREFIX_SERVER_ANSWER = resourceBundle.getString("standardMessages.prefixServerAnswer");
        UNSUCCESSFULLY_COMMAND_MESSAGE = resourceBundle.getString("exceptionMessages.unsuccessfullyCommand");
        OFFER_TO_REPEAT_INPUT = resourceBundle.getString("standardMessages.offerToRepeatInput");
        GOOD_BYE_LINE_1 = resourceBundle.getString("standardMessages.goodBye.line1");
        GOOD_BYE_LINE_2 = resourceBundle.getString("standardMessages.goodBye.line2");
        CONNECTED_SUCCESSFULLY_MESSAGE = resourceBundle.getString("standardMessages.connectedSuccessfully");
    }


    public String showGreetingMessage() {
        return GREETINGS_LINE_1 + System.lineSeparator() + GREETINGS_LINE_2 + System.lineSeparator();
    }
    
    public String showInternalServerErrorMessage() {
        return getErrorPrefix() + INTERNAL_SERVER_ERROR_MESSAGE;
    }

    public String showInternalClientErrorMessage() {
        return getErrorPrefix() + INTERNAL_CLIENT_ERROR_MESSAGE;
    }

    public String showClientCannotConnectToServerErrorMessage() {
        return getErrorPrefix() + CLIENT_CANNOT_CONNECT_TO_SERVER_ERROR_MESSAGE;
    }

    public String showClientLostConnectionErrorMessage() {
        return getErrorPrefix() + LOST_CONNECTION_ERROR_MESSAGE;
    }

    public String showGetWrongMessageErrorMessage() {
        return getErrorPrefix() + GET_WRONG_MESSAGE_ERROR_MESSAGE;
    }

    public String showNotYetConnectedErrorMessage() {
        return getErrorPrefix() + NOT_YET_CONNECTED_ERROR_MESSAGE;
    }

    public String showConnectedSuccessfullyMessage() {
        return CONNECTED_SUCCESSFULLY_MESSAGE;
    }

    public String showBadRequestErrorMessage() {
        return getErrorPrefix() + BAD_REQUEST_ERROR_MESSAGE;
    }

    public String showNullEnterInput() {
        return NULL_ENTER_INPUT;
    }

    public String showPrefixServerAnswer() {
        return PREFIX_SERVER_ANSWER;
    }

    public String showUnsuccessfulCommandMessage() {
        return UNSUCCESSFULLY_COMMAND_MESSAGE;
    }

    public String showNoSuchCommandErrorMessage() {
        return getErrorPrefix() + NO_SUCH_COMMAND_ERROR_MESSAGE;
    }
    
    public String showOfferToRepeatInput() {
        return OFFER_TO_REPEAT_INPUT;
    }
    
    public String showGoodbyeMessage() {
        return GOOD_BYE_LINE_1 + System.lineSeparator() + GOOD_BYE_LINE_2;
    }
    
    public String getErrorPrefix() {
        return ERROR_PREFIX;
    }
}
