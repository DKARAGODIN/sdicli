package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Runner;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteCommandTest {

    @Test
    public void test() throws Exception {
        Runner runner = new Runner();
        Command command = new ExecuteCommand();
        command.setArguments(Arrays.asList("java", "-version"));
        Reader reader = runner.run(List.of(command));

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }

        String version = Runtime.version().version().get(0).toString();
        assertTrue(actualBuffer.toString().contains(version));
    }
}