package app.commands.script.argumentFormer;

import app.commands.script.Script;
import app.commands.script.ScriptException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ArgumentFormer {
    protected final List<String> arguments = new ArrayList<>();

    public abstract List<String> collectArguments(Script script);
}
