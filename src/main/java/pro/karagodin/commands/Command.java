package pro.karagodin.commands;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import pro.karagodin.exceptions.CLIException;

public abstract class Command {

    /**
     * Represents exit code of the command. {@Link Runner} can check this value
     */
    protected int exitCode = 0;
    /**
     * Arguments used to specify children behaviour
     */
    protected List<String> arguments = new ArrayList<>();

    /**
     * Method to fire command. {@code reader} represents input stream for the command.
     * @param reader
     * @return {@link Reader} output stream - result of command execution
     * @throws CLIException
     */
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
