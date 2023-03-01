package pro.karagodin;

import pro.karagodin.commands.Command;
import pro.karagodin.commands.ExecuteCommand;
import pro.karagodin.exceptions.CLIException;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class Runner {

    private int lastCommandExitCode = 0;

    public Reader run(List<Command> commands) throws CLIException {
        Reader reader = null;
        for (Command command : commands) {
            if (command instanceof ExecuteCommand)
                command.setRunner(this);

            reader = command.run(reader);

            if (lastCommandExitCode != 0)
                break;
        }
        return reader == null ? new StringReader("") : reader;
    }

    public int getLastCommandExitCode() {
        return lastCommandExitCode;
    }

    public void setLastCommandExitCode(int lastCommandExitCode) {
        this.lastCommandExitCode = lastCommandExitCode;
    }
}