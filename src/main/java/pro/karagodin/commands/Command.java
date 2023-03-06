package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    protected int exitCode = 0;
    protected List<String> arguments = new ArrayList<>();

    public abstract Reader run(Reader reader) throws CLIException;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command cmd = (Command) o;
        return cmd.arguments.equals(arguments) && exitCode == cmd.exitCode;
    }   

    @Override
    public String toString() {
        return "command with args: " + arguments.toString();
    }
}
