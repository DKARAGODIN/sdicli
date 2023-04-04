package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Environment;
import pro.karagodin.Runner;
import pro.karagodin.exceptions.CLIException;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LsCommandTest {
    @Test
    public void empty() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command cd = new CdCommand();
        cd.setArguments(Arrays.asList("src/test/resources/commands"));
        commands.add(cd);

        Command ls = new LsCommand();
        commands.add(ls);

        var reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        String expected = "text3.txt text2.txt text1.txt";

        assertEquals(expected, actual);
    }

    @Test
    public void oneArgumentDirectry() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command cd = new CdCommand();
        commands.add(cd);

        System.out.println(Environment.getVariableValue("PWD"));

        Command ls = new LsCommand();
        ls.setArguments(Arrays.asList("src/test/resources/commands"));
        commands.add(ls);
        var reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        String expected = "text3.txt text2.txt text1.txt";

        assertEquals(expected, actual);
    }


    @Test
    public void oneArgumentFile() throws Exception {
        List<Command> commands = new ArrayList<>();

        Command cd = new CdCommand();
        commands.add(cd);

        Command a = new LsCommand();
        a.setArguments(Arrays.asList("src/test/resources/commands/text1.txt"));
        commands.add(a);
        var reader = Runner.run(commands);

        BufferedReader br = new BufferedReader(reader);
        String actual = br.readLine();
        String expected = "text1.txt";

        assertEquals(expected, actual);
    }


    @Test
    public void badArgument() {
        List<Command> commands = new ArrayList<>();

        Command cd = new CdCommand();
        commands.add(cd);

        Command a = new LsCommand();
        a.setArguments(Arrays.asList("this_is_bad_argument"));
        commands.add(a);

        assertThrows(
                CLIException.class,
                () -> Runner.run(commands),
                "No such directory"
        );
    }

}