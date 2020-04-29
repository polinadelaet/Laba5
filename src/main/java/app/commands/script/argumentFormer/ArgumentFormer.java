package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.scriptException.ScriptException;

import java.util.ArrayList;
import java.util.List;

public abstract class ArgumentFormer {

    protected final List<String> arguments = new ArrayList<>();

    public abstract List<String> collectArguments(Script script) throws ScriptException;

}
