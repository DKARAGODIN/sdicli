package pro.karagodin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import pro.karagodin.commands.CatCommand;

public class ParserTest {
    @Test
    void testParseSimpleCmd() {
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
        assertEquals(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }

    @Test
    void testParseCmdWithMultipleSpaces() {
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
        assertEquals(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }

    @Test
    void testParseCmdWithNotTrimedSpaces() {
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
        assertEquals(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }

    @Test
    void testParseCmdWithoutArgs() {
        var lexemes = new Lexeme[] {
                new Lexeme("cat", LexemeType.STR),
        };
        var expectedCmds = List.of(new CatCommand());
        expectedCmds.get(0).setArguments(List.of());
        var parser = new Parser();
        assertEquals(expectedCmds, parser.parse(Arrays.asList(lexemes)));
    }
}
