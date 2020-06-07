package app.controller.exception;

import middleware.MiddlewareException;

public class ControllerException extends MiddlewareException {
    public ControllerException() {
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }
}
