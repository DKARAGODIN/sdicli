package pro.karagodin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ScannerTest {
    @Test
    void testScanWithoutQuotes(){
        String str = "cat file1 file2";
        Lexeme[] expected = new Lexeme[]{
            new Lexeme("cat", LexemeType.STR),
            new Lexeme(" ", LexemeType.SPACE),
            new Lexeme("file1", LexemeType.STR),
            new Lexeme(" ", LexemeType.SPACE),
            new Lexeme("file2", LexemeType.STR)
        };
        Scanner scanner = new Scanner();
        assertIterableEquals(Arrays.asList(expected), scanner.scan(str));
    }

    @Test
    void testScanWithQuotes(){
        String str = "cat \"file name\" 'file2 name'";
        Lexeme[] expected = new Lexeme[]{
            new Lexeme("cat", LexemeType.STR),
            new Lexeme(" ", LexemeType.SPACE),
            new Lexeme("file name", LexemeType.DQ),
            new Lexeme(" ", LexemeType.SPACE),
            new Lexeme("file2 name", LexemeType.SQ)
        };
        Scanner scanner = new Scanner();
        assertIterableEquals(Arrays.asList(expected), scanner.scan(str));
    }

    @Test
    void testScanWithNestedQuotes(){
        String str = "cat \"file 'name'\" 'file2 \"name\"'";
        Lexeme[] expected = new Lexeme[]{
            new Lexeme("cat", LexemeType.STR),
            new Lexeme(" ", LexemeType.SPACE),
            new Lexeme("file 'name'", LexemeType.DQ),
            new Lexeme(" ", LexemeType.SPACE),
            new Lexeme("file2 \"name\"", LexemeType.SQ)
        };
        Scanner scanner = new Scanner();
        assertIterableEquals(Arrays.asList(expected), scanner.scan(str));
    }

    @Test
    void testScanWithSequenceLexemes(){
        String str = "cat \"file\"' name'";
        Lexeme[] expected = new Lexeme[]{
            new Lexeme("cat", LexemeType.STR),
            new Lexeme(" ", LexemeType.SPACE),
            new Lexeme("file", LexemeType.DQ),
            new Lexeme(" name", LexemeType.SQ)
        };
        Scanner scanner = new Scanner();
        assertIterableEquals(Arrays.asList(expected), scanner.scan(str));
    }
}
