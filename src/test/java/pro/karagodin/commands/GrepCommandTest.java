package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Runner;
import pro.karagodin.exceptions.CLIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GrepCommandTest {
    @Test
    public void testInvalidArguments() {
        {
            GrepCommand grepCommand = new GrepCommand();
            List<String> args = new ArrayList<>();
            args.add("-H");
            grepCommand.setArguments(args);
            assertThrows(CLIException.class, () -> {
                grepCommand.run(new StringReader(""));
            });
        }
        {
            GrepCommand grepCommand = new GrepCommand();
            List<String> args = new ArrayList<>();
            args.add("-A");
            grepCommand.setArguments(args);
            assertThrows(CLIException.class, () -> {
                grepCommand.run(new StringReader(""));
            });
        }
        {
            GrepCommand grepCommand = new GrepCommand();
            List<String> args = new ArrayList<>();
            args.add("-");
            grepCommand.setArguments(args);
            assertThrows(CLIException.class, () -> {
                grepCommand.run(new StringReader(""));
            });
        }
        {
            GrepCommand grepCommand = new GrepCommand();
            List<String> args = new ArrayList<>();
            args.add("-i");
            grepCommand.setArguments(args);
            assertThrows(CLIException.class, () -> {
                grepCommand.run(new StringReader(""));
            });
        }
        {
            GrepCommand grepCommand = new GrepCommand();
            List<String> args = new ArrayList<>();
            args.add("-A");
            grepCommand.setArguments(args);
            assertThrows(CLIException.class, () -> {
                grepCommand.run(new StringReader(""));
            });
        }
        {
            GrepCommand grepCommand = new GrepCommand();
            List<String> args = new ArrayList<>();
            args.add("-w");
            grepCommand.setArguments(args);
            assertThrows(CLIException.class, () -> {
                grepCommand.run(new StringReader(""));
            });
        }
    }

    @Test
    public void testGrepA() throws CLIException, IOException {
        GrepCommand grepCommand = new GrepCommand();
        List<String> args = new ArrayList<>();
        args.add("-A");
        args.add("1");
        args.add("Hello");
        args.add(Paths.get("", "src", "test", "resources", "commands", "text2.txt").toString());
        args.add(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString());
        grepCommand.setArguments(args);

        Runner runner = new Runner();

        Reader reader = runner.run(List.of(grepCommand));

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
                "This text was made to test grep command." + System.lineSeparator() +
                "Hello world!" + System.lineSeparator() +
                "Congratulations, you observe the best piece of software ever made." + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void testGrepA_1() throws CLIException, IOException {
        GrepCommand grepCommand = new GrepCommand();
        List<String> args = new ArrayList<>();
        args.add("-A");
        args.add("1");
        args.add("grep");
        args.add(Paths.get("", "src", "test", "resources", "commands", "text2.txt").toString());
        args.add(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString());
        grepCommand.setArguments(args);

        Runner runner = new Runner();

        Reader reader = runner.run(List.of(grepCommand));

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }
        String actual = actualBuffer.toString();
        String expected =
                "This text was made to test grep command." + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void testGrepR() throws CLIException, IOException {
        GrepCommand grepCommand = new GrepCommand();
        List<String> args = new ArrayList<>();
        args.add("grep");
        args.add(Paths.get("", "src", "test", "resources", "commands", "text2.txt").toString());
        args.add(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString());
        grepCommand.setArguments(args);

        Runner runner = new Runner();

        Reader reader = runner.run(List.of(grepCommand));

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }
        String actual = actualBuffer.toString();
        String expected =
                "This text was made to test grep command." + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void testGrepW() throws CLIException, IOException {
        GrepCommand grepCommand = new GrepCommand();
        List<String> args = new ArrayList<>();
        args.add("-w");
        args.add("Hello world!");
        args.add(Paths.get("", "src", "test", "resources", "commands", "text2.txt").toString());
        args.add(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString());
        grepCommand.setArguments(args);

        Runner runner = new Runner();

        Reader reader = runner.run(List.of(grepCommand));

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
                "Hello world!" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void testGrepI() throws CLIException, IOException {
        GrepCommand grepCommand = new GrepCommand();
        List<String> args = new ArrayList<>();
        args.add("-i");
        args.add("HeLlO WoRlD!");
        args.add(Paths.get("", "src", "test", "resources", "commands", "text2.txt").toString());
        args.add(Paths.get("", "src", "test", "resources", "commands", "text3.txt").toString());
        grepCommand.setArguments(args);

        Runner runner = new Runner();

        Reader reader = runner.run(List.of(grepCommand));

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
                        "Hello world!" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void testOnlyReader() throws Exception {
        Runner runner = new Runner();
        List<Command> commands = new ArrayList<>();

        Command first = new EchoCommand();
        first.setArguments(List.of("Hello world!"));
        commands.add(first);
        GrepCommand grepCommand = new GrepCommand();
        List<String> args = new ArrayList<>();
        args.add("-i");
        args.add("HeLlO WoRlD!");
        grepCommand.setArguments(args);
        commands.add(grepCommand);

        Reader reader = runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }
        String actual = actualBuffer.toString();
        String expected =
                "Hello world!" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}