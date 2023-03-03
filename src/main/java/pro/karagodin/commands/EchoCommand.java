package pro.karagodin.commands;

import java.io.Reader;
import java.io.StringReader;
import java.util.stream.Collectors;

public class EchoCommand extends Command {

    @Override
    public Reader run(Reader reader) {
        return new StringReader(arguments.stream().collect(Collectors.joining(" ")));
    }
}
