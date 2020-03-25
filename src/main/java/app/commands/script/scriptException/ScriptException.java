package app.commands.script.scriptException;

public class ScriptException extends Exception {
    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }

    public ScriptException() {
    }
}
