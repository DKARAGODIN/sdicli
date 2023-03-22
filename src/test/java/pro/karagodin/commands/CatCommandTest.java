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

class CatCommandTest {
    @Test
    public void testArgument() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new CatCommand();
        first.setArguments(List.of(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString()));
        commands.add(first);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }
        String actual = actualBuffer.toString();
        String expected = "Hello world!" + System.lineSeparator() +
            "Congratulations, you observe the best piece of software ever made." + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void testArguments() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new CatCommand();
        first.setArguments(Arrays.asList(
                Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString(),
                Paths.get("", "src", "test", "resources", "commands", "text1.txt").toString()));
        commands.add(first);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }
        String actual = actualBuffer.toString();
        String expected =
                "Hello world!" + System.lineSeparator() +
                "Congratulations, you observe the best piece of software ever made." + System.lineSeparator() +
                "Hello world" + System.lineSeparator() +
                "This is simple text" + System.lineSeparator() +
                "Bye world" + System.lineSeparator() +
                ":)(:" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void testOnlyReader() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(List.of("Hello world!"));
        commands.add(first);
        Command second = new CatCommand();
        commands.add(second);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        assertEquals("Hello world!", actual);
    }

    @Test
    public void testArgumentsAndReader() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(List.of("Hello world!"));
        commands.add(first);
        Command second = new CatCommand();
        second.setArguments(Arrays.asList(
                Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString(),
                Paths.get("", "src", "test", "resources", "commands", "text1.txt").toString()));
        commands.add(second);
        Reader reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }
        String actual = actualBuffer.toString();
        String expected =
                "Hello world!" + System.lineSeparator() +
                "Congratulations, you observe the best piece of software ever made." + System.lineSeparator() +
                "Hello world" + System.lineSeparator() +
                "This is simple text" + System.lineSeparator() +
                "Bye world" + System.lineSeparator() +
                ":)(:" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}