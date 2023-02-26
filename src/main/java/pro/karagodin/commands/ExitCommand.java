package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

import java.io.Reader;

public class ExitCommand extends Command {
    @Override
    public Reader run(Reader reader) throws CLIException {
        CLIException exception = new CLIException("Good bye");
        exception.setExit(true);
        throw exception;
    }
}
