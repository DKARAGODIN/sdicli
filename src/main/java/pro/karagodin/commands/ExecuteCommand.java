package pro.karagodin.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import pro.karagodin.Environment;
import pro.karagodin.exceptions.CLIException;

public class ExecuteCommand extends Command {

    private final String command;

    public ExecuteCommand(String command) {
        this.command = command;
    }

    /**
     * TODO Rewrite with multithreading, cleaner resources handling
     * Executes {@link command} in separate process. All content from {@code reader} will be
     * directed to standard input of created process. Result of process execution from standard output and error streams
     * will be combined and returned. Exit code will be set in the {@link exitCode} field.
     * @param reader
     * @return
     * @throws CLIException
     */

    @Override
    public Reader run(Reader reader) throws CLIException {
        Reader result;
        Runtime runtime = Runtime.getRuntime();

        try {
            Process p = runtime.exec(getCmdarray(), getEnvp());
            OutputStream processInput = p.getOutputStream();
            try (BufferedWriter bufferedProcessInput = new BufferedWriter(new OutputStreamWriter(processInput, StandardCharsets.UTF_8));
                 BufferedReader standardOut = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
                 BufferedReader standardErr = new BufferedReader(new InputStreamReader(p.getErrorStream(), StandardCharsets.UTF_8))) {

                String line = null;
                if (reader != null) {
                    BufferedReader inputReader = new BufferedReader(reader);
                    while (true) {
                        line = inputReader.readLine();
                        if (line == null) {
                            bufferedProcessInput.close();
                            break;
                        }
                        bufferedProcessInput.write(line);
                        bufferedProcessInput.write(System.lineSeparator());
                    }
                }

                StringBuilder sb = new StringBuilder();
                line = null;
                while ((line = standardOut.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                line = null;
                while ((line = standardErr.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }

                this.exitCode = p.waitFor();
                result = new StringReader(sb.toString());

            } catch (IOException | InterruptedException e) {
                throw new CLIException("Exception happened while executing command", e);
            }
        } catch (IOException e) {
            throw new CLIException("Exception happened while executing command", e);
        }
        return result;
    }

    private String[] getCmdarray() {
        String[] cmdarray = new String[arguments.size() + 1];
        cmdarray[0] = command;
        for (int i = 0; i < arguments.size(); i++) {
            cmdarray[i + 1] = arguments.get(i);
        }
        return cmdarray;
    }

    private String[] getEnvp() {
        String[] envp = new String[Environment.getEnvironmentSize()];
        int i = 0;
        for (Map.Entry<String, String> entry : Environment.getEntriesSet()) {
            envp[i] = entry.getKey() + "=" + entry.getValue();
            i++;
        }
        return envp;
    }
}
