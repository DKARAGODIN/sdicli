package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

import java.io.Reader;
import java.util.List;
import java.util.Random;

public class ExitCommand extends Command {

    private static final List<String> GOODBYES =
            List.of("Good bye", "See you soon", "Bis bald", "Au revoir", "It was awesome, you are awesome", "Till next time", "I'll be missing you");
    private Random rand = new Random();

    @Override
    public Reader run(Reader reader) throws CLIException {
        CLIException exception = new CLIException(GOODBYES.get(rand.nextInt(GOODBYES.size())));
        exception.setExit(true);
        exception.setNeedToPrintStackTrace(false);
        throw exception;
    }
}
