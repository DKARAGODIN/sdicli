package pro.karagodin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import pro.karagodin.commands.CatCommand;
import pro.karagodin.commands.Command;
import pro.karagodin.exceptions.CLIException;

public class ParserTest {
    @Test
    void testParseSimpleCmd() throws CLIException {
        var lexemes = new Lexeme[] {
                new Lexeme("cat", LexemeType.STR),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("file1", LexemeType.DQ),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("file2", LexemeType.STR)
        };
        var expectedCmds = List.of(new CatCommand());
        expectedCmds.get(0).setArguments(List.of("file1", "file2"));
        var parser = new Parser();
        assertListOfCommands(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }

    @Test
    void testParseCmdWithMultipleSpaces() throws CLIException {
        var lexemes = new Lexeme[] {
                new Lexeme("cat", LexemeType.STR),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("file1", LexemeType.DQ),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("file2", LexemeType.STR)
        };
        var expectedCmds = List.of(new CatCommand());
        expectedCmds.get(0).setArguments(List.of("file1", "file2"));
        var parser = new Parser();
        assertListOfCommands(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }

    @Test
    void testParseCmdWithNotTrimedSpaces() throws CLIException {
        var lexemes = new Lexeme[] {
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("cat", LexemeType.STR),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("file1", LexemeType.DQ),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("file2", LexemeType.STR),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme(" ", LexemeType.SPACE),
        };
        var expectedCmds = List.of(new CatCommand());
        expectedCmds.get(0).setArguments(List.of("file1", "file2"));
        var parser = new Parser();
        assertListOfCommands(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }

    @Test
    void testParseCmdWithoutArgs() throws CLIException {
        var lexemes = new Lexeme[] {
                new Lexeme("cat", LexemeType.STR),
        };
        var expectedCmds = List.of(new CatCommand());
        expectedCmds.get(0).setArguments(List.of());
        var parser = new Parser();
        assertListOfCommands(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }

    private void assertListOfCommands(List<? extends Command> expected, List<Command> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getExitCode(), actual.get(i).getExitCode());
            assertEquals(expected.get(i).getArguments(), actual.get(i).getArguments());
        }
    }
}
