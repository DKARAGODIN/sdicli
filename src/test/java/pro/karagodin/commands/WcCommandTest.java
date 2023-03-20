package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Runner;

import java.io.BufferedReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WcCommandTest {

    @Test
    public void testArgumenst() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new WcCommand();
        first.setArguments(Arrays.asList(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString()));
        commands.add(first);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertEquals("2 12 78", actual);
    }

    @Test
    public void testArgumenstAndReader() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(Arrays.asList("Hello world!"));
        commands.add(first);
        Command second = new WcCommand();
        second.setArguments(Arrays.asList(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString()));
        commands.add(second);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertEquals("2 12 78", actual);
    }

    @Test
    public void testOnlyReader() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(Arrays.asList("Hello world!"));
        commands.add(first);
        Command second = new WcCommand();
        commands.add(second);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertEquals("1 2 12", actual);
    }
}