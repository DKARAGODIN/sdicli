package pro.karagodin.commands;

import java.io.Reader;
import java.io.StringReader;

public class EchoCommand extends Command {

    @Override
    public Reader run(Reader reader) {
        if (arguments.isEmpty()) {
            return new StringReader("");
        } else {
            return new StringReader(String.join(" ", arguments));
        }
    }
}
