package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;

public interface Command {

    void setArguments(List<String> args);

    Writer run(Reader reader) throws CLIException;

}
