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

class PwdCommandTest {
    @Test
    public void empty() throws Exception {
        Runner runner = new Runner();
        List<Command> commands = new ArrayList<>();

        Command a = new PwdCommand();
        commands.add(a);
        Reader reader = runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        String expected = Paths.get("").toAbsolutePath().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void arguments() throws Exception {
        Runner runner = new Runner();
        List<Command> commands = new ArrayList<>();

        Command a = new PwdCommand();
        a.setArguments(Arrays.asList("Hello world"));
        commands.add(a);
        Reader reader = runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        String expected = Paths.get("").toAbsolutePath().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void incomingReader() throws Exception {
        Runner runner = new Runner();
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(Arrays.asList("First echo command arguments"));
        commands.add(first);
        Command second = new PwdCommand();
        commands.add(second);
        Reader reader = runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        String expected = Paths.get("").toAbsolutePath().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void incomingReaderAndArguments() throws Exception {
        Runner runner = new Runner();
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(Arrays.asList("First echo command arguments"));
        commands.add(first);
        Command second = new PwdCommand();
        commands.add(second);
        second.setArguments(Arrays.asList("Second echo command arguments "));
        Reader reader = runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        String expected = Paths.get("").toAbsolutePath().toString();
        assertEquals(expected, actual);
    }
}