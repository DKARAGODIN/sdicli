package pro.karagodin.commands;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import pro.karagodin.exceptions.CLIException;

public abstract class Command {

    protected int exitCode = 0;
    protected List<String> arguments = new ArrayList<>();

    public abstract Reader run(Reader reader) throws CLIException;

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    @Override
    public String toString() {
        return "Command with args: " + arguments.toString();
    }
}
