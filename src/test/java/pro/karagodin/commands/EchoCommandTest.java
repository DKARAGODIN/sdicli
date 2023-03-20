package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Runner;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EchoCommandTest {

    @Test
    public void empty() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command a = new EchoCommand();
        commands.add(a);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertNull(actual);
    }

    @Test
    public void arguments() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command a = new EchoCommand();
        a.setArguments(Arrays.asList("Hello world"));
        commands.add(a);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertEquals("Hello world", actual);
    }

    @Test
    public void incomingReader() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(Arrays.asList("First echo command arguments"));
        commands.add(first);
        Command second = new EchoCommand();
        commands.add(second);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertNull(actual);
    }

    @Test
    public void incomingReaderAndArguments() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(Arrays.asList("First echo command arguments"));
        commands.add(first);
        Command second = new EchoCommand();
        commands.add(second);
        second.setArguments(Arrays.asList("Second echo command arguments "));
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertEquals("Second echo command arguments ", actual);
    }
}