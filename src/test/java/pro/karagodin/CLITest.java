package pro.karagodin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pro.karagodin.exceptions.CLIException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CLITest {

    private static final String EOL = System.lineSeparator();

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private void runInterpreter() {
        var cli = new CLI();
        try {
            cli.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    @Order(1)
    public void testOneCommand() {
        provideInput("echo hello world" + EOL);
        runInterpreter();
        String actual = getOutput();
        assertEquals("> hello world" + EOL + "> ", actual);
    }

    @Test
    @Order(2)
    public void testTwoLines() {
        provideInput("echo hello world \n echo hello world " + EOL);
        runInterpreter();
        String actual = getOutput();
        assertEquals("> hello world" + EOL + "> hello world" + EOL + "> ", actual);
    }

    @Test
    @Order(3)
    public void testExit() {
        provideInput("exit " + EOL);

        List<String> exited = new ArrayList<>();
        var cli =  new CLI() {
            @Override
            public void exit(CLIException e) {
                exited.add("Exited");
            }
        };
        try {
            cli.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String actual = getOutput();
        assertNotNull(actual);
        assertFalse(exited.isEmpty());
    }


}
