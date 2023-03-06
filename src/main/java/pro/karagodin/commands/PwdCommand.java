package pro.karagodin.commands;

import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;

import pro.karagodin.exceptions.CLIException;

public class PwdCommand extends Command {
    @Override
    public Reader run(Reader reader) throws CLIException {
        String s = Paths.get("").toAbsolutePath().toString();
        return new StringReader(s);
    }
}
