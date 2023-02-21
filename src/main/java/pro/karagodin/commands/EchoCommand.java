package pro.karagodin.commands;

import java.io.Reader;
import java.io.StringReader;
import java.util.stream.Collectors;

public class EchoCommand extends Command {

    @Override
    public Reader run(Reader reader) {
        if (arguments.isEmpty()) {
            return reader == null ? new StringReader("") : reader;
        } else {
            return new StringReader(arguments.stream().collect(Collectors.joining(" ")));
        }
    }
}
