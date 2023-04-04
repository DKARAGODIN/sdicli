package pro.karagodin.commands;

import org.junit.jupiter.api.Test;
import pro.karagodin.Environment;
import pro.karagodin.Runner;
import pro.karagodin.exceptions.CLIException;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CdCommandTest {

    private String ProcessCd(String arg) throws Exception {
        List<Command> commands = new ArrayList<>();

        Command a = new CdCommand();
        if (arg != null) { a.setArguments(Arrays.asList(arg)); }
        commands.add(a);
        Runner.run(commands);

        return Environment.getVariableValue("PWD");
    }

    @Test
    public void empty() throws Exception {
        String actual = ProcessCd(null);
        String expected = Paths.get("/home/" + Environment.getVariableValue("USER")).toAbsolutePath().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void oneStepForward() throws Exception {
        ProcessCd( System.getProperty("user.dir") );
        String actual = ProcessCd("src");
        String expected = Paths.get("src").toAbsolutePath().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void twoStepsForward() throws Exception {
        ProcessCd( System.getProperty("user.dir") );
        String actual = ProcessCd("src/main");
        String expected = Paths.get("src/main").toAbsolutePath().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void oneStepBackward() throws Exception {
        ProcessCd(System.getProperty("user.dir") + "/src" );

        String actual = ProcessCd("../");
        String expected = System.getProperty("user.dir");
        assertEquals(expected, actual);
    }

    @Test
    public void twoStepsBackward() throws Exception {
        ProcessCd(System.getProperty("user.dir") + "/src/main" );

        String actual = ProcessCd("../../");
        String expected = System.getProperty("user.dir");
        assertEquals(expected, actual);
    }

    @Test
    public void toSibling() throws Exception {
        ProcessCd(System.getProperty("user.dir") + "/src/main");

        String expected = System.getProperty("user.dir") + "/src/test";
        String actual = ProcessCd("../test");
        assertEquals(expected, actual);
    }

    @Test
    public void toUncle() throws Exception {
        ProcessCd(System.getProperty("user.dir") + "/src/main/java");

        String expected = System.getProperty("user.dir") + "/src/test";
        String actual = ProcessCd("../../test");
        assertEquals(expected, actual);
    }

    @Test
    public void badArgument() {
        assertThrows(CLIException.class, () -> ProcessCd("bad_argument"), "No such directory");
    }

}