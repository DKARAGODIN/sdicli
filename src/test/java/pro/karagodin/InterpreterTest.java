package pro.karagodin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pro.karagodin.exceptions.CLIException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class InterpreterTest {

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

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    @Order(1)
    public void test() throws InterruptedException {
        provideInput("echo hello world" + EOL);

        Runnable runnable = () -> {
            Interpreter interpreter = new Interpreter();
            try {
                interpreter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(250);
        String actual = getOutput();
        assertEquals("> hello world" + EOL + "> ", actual);
    }

    @Test
    @Order(2)
    public void testPipe() throws InterruptedException {
        provideInput("echo hello | wc" + EOL);

        Runnable runnable = () -> {
            Interpreter interpreter = new Interpreter();
            try {
                interpreter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(250);
        String actual = getOutput();
        assertEquals("> 1 1 5" + EOL + "> ", actual);
    }

    @Test
    @Order(3)
    public void testLongPipe() throws InterruptedException {
        provideInput("echo hello world | wc | cat " + EOL);

        Runnable runnable = () -> {
            Interpreter interpreter = new Interpreter();
            try {
                interpreter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(250);
        String actual = getOutput();
        assertEquals("> 1 2 11" + EOL + "> ", actual);
    }

    @Test
    @Order(4)
    public void testLongPipe2() throws InterruptedException {
        provideInput("echo hello world | wc | cat | wc" + EOL);

        Runnable runnable = () -> {
            Interpreter interpreter = new Interpreter();
            try {
                interpreter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(250);
        String actual = getOutput();
        assertEquals("> 1 3 6" + EOL + "> ", actual);
    }

    @Test
    @Order(5)
    public void testTwoLines() throws InterruptedException {
        provideInput("echo hello world \n echo hello world " + EOL);

        Runnable runnable = () -> {
            Interpreter interpreter = new Interpreter();

            try {
                interpreter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(250);
        String actual = getOutput();
        assertEquals("> hello world" + EOL + "> hello world" + EOL + "> ", actual);
    }

    @Test
    @Order(6)
    public void testExit() throws InterruptedException {
        provideInput("exit " + EOL);

        List<String> exited = new ArrayList<>();

        Runnable runnable = () -> {
            Interpreter interpreter = new Interpreter() {
                @Override
                public void exit(CLIException e) {
                    exited.add("Exited");
                }
            };
            try {
                interpreter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(250);
        String actual = getOutput();
        assertNotNull(actual);
        assertFalse(exited.isEmpty());
    }

    @Test
    @Order(7)
    public void testPwd() throws InterruptedException {
        provideInput("pwd " + EOL);

        Runnable runnable = () -> {
            Interpreter interpreter = new Interpreter();
            try {
                interpreter.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(250);
        String actual = getOutput();
        String expected = Paths.get("").toAbsolutePath().toString();
        assertEquals("> " + expected + EOL + "> ", actual);
    }
}
