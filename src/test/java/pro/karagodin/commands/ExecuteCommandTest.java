package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Runner;

import java.io.BufferedReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteCommandTest {

    private static final String SEP = FileSystems.getDefault().getSeparator();

    @Test
    public void test() throws Exception {
        Command command = new ExecuteCommand("java");
        command.setArguments(Arrays.asList("-cp","."+ SEP +"target"+ SEP +"test-classes"+ SEP, "pro.karagodin.commands.MainTest"));
        Reader reader = Runner.run(List.of(command));

        BufferedReader br = new BufferedReader(reader);
        StringBuilder actualBuffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            actualBuffer.append(line);
            actualBuffer.append(System.lineSeparator());
        }

        assertTrue(actualBuffer.toString().contains("Hello java!"));
    }
}