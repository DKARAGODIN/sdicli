package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.exceptions.CLIException;

import java.io.StringReader;
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
}