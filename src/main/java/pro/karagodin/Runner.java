package pro.karagodin;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import pro.karagodin.commands.Command;
import pro.karagodin.exceptions.CLIException;

public class Runner {

    public Reader run(List<Command> commands) throws CLIException {
        Reader reader = null;
        for (Command command : commands) {
            reader = command.run(reader);

            if (command.getExitCode() != 0)
                break;
        }
        return reader == null ? new StringReader("") : reader;
    }
}
