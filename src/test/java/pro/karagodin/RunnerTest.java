package pro.karagodin;

import org.junit.jupiter.api.Test;
import pro.karagodin.commands.Command;
import pro.karagodin.commands.EchoCommand;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunnerTest {

    private Runner runner = new Runner();

    @Test
    public void test() throws Exception {
        List<Command> commands = new ArrayList<>();
        Command a = new EchoCommand();
        a.setArguments(Arrays.asList("Hello world"));
        commands.add(a);
        Reader reader = runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertEquals("Hello world", actual);
    }
}