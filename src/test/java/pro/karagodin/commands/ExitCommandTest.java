package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Runner;
import pro.karagodin.exceptions.CLIException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {
    @Test
    public void textException() {
        assertThrows(CLIException.class, () -> {
            Runner runner = new Runner();
            Command command = new ExitCommand();
            runner.run(List.of(command));
        });
    }
}