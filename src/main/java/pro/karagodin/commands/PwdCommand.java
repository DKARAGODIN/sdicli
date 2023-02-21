package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;

public class PwdCommand extends Command {
    @Override
    public Reader run(Reader reader) throws CLIException {
        String s = Paths.get("").toAbsolutePath().toString();
        return new StringReader(s);
    }
}
