package pro.karagodin;

import org.junit.jupiter.api.Test;
import pro.karagodin.exceptions.CLIException;

import java.io.*;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class InterpreterTest {

    private String getInterpreterOutput(String line) throws CLIException, IOException {
        var interpreter = new Interpreter();
        try (var reader = interpreter.interpret(line)) {
            return new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
        }
    }

    @Test
    public void testPipe() throws CLIException, IOException {
        assertEquals("1 1 5", getInterpreterOutput("echo hello | wc"));
    }

    @Test
    public void testLongPipe() throws CLIException, IOException {
        assertEquals("1 2 11", getInterpreterOutput("echo hello world | wc | cat "));
    }

    @Test
    public void testLongPipe2() throws CLIException, IOException {
        assertEquals("1 3 6", getInterpreterOutput("echo hello world | wc | cat | wc"));
    }

    @Test
    public void testPwd() throws CLIException, IOException {
        assertEquals(Paths.get("").toAbsolutePath().toString(), getInterpreterOutput("pwd "));
    }

    @Test
    public void testThrowsWhenEmptyCommandAfterPipe() {
        assertThrows(CLIException.class, () -> getInterpreterOutput("pwd | "));
    }

    @Test
    public void testThrowsWhenEmptyCommandBeforePipe() {
        assertThrows(CLIException.class, () -> getInterpreterOutput(" | pwd "));
    }

    @Test
    public void testThrowsWhenEmptyCommandBetweenPipe() {
        assertThrows(CLIException.class, () -> getInterpreterOutput("echo a | | cat "));
    }

    @Test
    public void testThrowsWhenNotClosedDoubleQuote() {
        assertThrows(CLIException.class, () -> getInterpreterOutput("echo \"abc"));
    }

    @Test
    public void testThrowsWhenNotClosedSingleQuote() {
        assertThrows(CLIException.class, () -> getInterpreterOutput("echo 'abc"));
    }


}
