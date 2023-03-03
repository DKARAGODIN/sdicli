package pro.karagodin.commands;

import pro.karagodin.Runner;
import pro.karagodin.exceptions.CLIException;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    protected int exitCode = 0;
    protected Runner runner = null;
    protected List<String> arguments = new ArrayList<>();

    public abstract Reader run(Reader reader) throws CLIException;

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
}
