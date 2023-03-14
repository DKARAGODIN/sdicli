package pro.karagodin;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import pro.karagodin.commands.CatCommand;
import pro.karagodin.commands.Command;
import pro.karagodin.commands.EchoCommand;
import pro.karagodin.exceptions.CLIException;

import static org.junit.jupiter.api.Assertions.*;

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
    void testParseCmdsWithPipes() throws CLIException {
        var lexemes = new Lexeme[] {
                new Lexeme("cat", LexemeType.STR),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("file1", LexemeType.DQ),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("|", LexemeType.PIPE),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("echo", LexemeType.STR),

        };
        var expectedCmds = List.of(new CatCommand(), new EchoCommand());
        expectedCmds.get(0).setArguments(List.of("file1"));
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


    @Test
    void testVariableAssign() throws CLIException{
        Lexeme[] lexemes = new Lexeme[]{
                new Lexeme("var", LexemeType.STR),
                new Lexeme("=", LexemeType.ASSIGN),
                new Lexeme("value", LexemeType.STR),
        };
        var parser = new Parser();
        assertNotEquals("value", Enviroment.getVariableValue("var"));
        var cmds =  parser.parse(Arrays.asList(lexemes));
        assertEquals(0, cmds.size());
        assertEquals("value", Enviroment.getVariableValue("var"));
    }



    @Test
    void testVariableAssignWithDifferentLexemes() throws CLIException{
        //var=v1 v2|v3="v4"'v5'
        Lexeme[] lexemes = new Lexeme[]{
                new Lexeme("var", LexemeType.STR),
                new Lexeme("=", LexemeType.ASSIGN),
                new Lexeme("v1", LexemeType.STR),
                new Lexeme(" ", LexemeType.SPACE),
                new Lexeme("v2", LexemeType.STR),
                new Lexeme("|", LexemeType.PIPE),
                new Lexeme("v3", LexemeType.STR),
                new Lexeme("=", LexemeType.ASSIGN),
                new Lexeme("v4", LexemeType.DQ),
                new Lexeme("v5", LexemeType.SQ),
        };
        var parser = new Parser();
        assertNotEquals("v1 v2|v3=v4v5", Enviroment.getVariableValue("var"));
        var cmds =  parser.parse(Arrays.asList(lexemes));
        assertEquals(0, cmds.size());
        assertEquals("v1 v2|v3=v4v5", Enviroment.getVariableValue("var"));
    }

    private void assertListOfCommands(List<? extends Command> expected, List<Command> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i).getExitCode(), actual.get(i).getExitCode());
            assertEquals(expected.get(i).getArguments(), actual.get(i).getArguments());
        }
    }
}
