package pro.karagodin.commands;

import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;

import pro.karagodin.Environment;
import pro.karagodin.exceptions.CLIException;

public class PwdCommand extends Command {

    /**
     * Returns present working directory string
     * @param reader
     * @return
     * @throws CLIException
     */
    @Override
    public Reader run(Reader reader) throws CLIException {
        String s = Paths.get(Environment.getVariableValue("PWD")).toAbsolutePath().toString();
        return new StringReader(s);
    }
}
