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
     */

    @Override
    public Reader run(Reader reader) throws CLIException {
        Runtime runtime = Runtime.getRuntime();
        BufferedWriter bufferedProcessInput = null;
        BufferedReader standardOut = null;
        BufferedReader standardErr = null;
        try {
            String[] cmdarray = new String[arguments.size() + 1];
            cmdarray[0] = command;
            for (int i = 0; i < arguments.size(); i++) {
                cmdarray[i + 1] = arguments.get(i);
            }
            Process p = runtime.exec(cmdarray, getEnvp());
            standardOut = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            standardErr = new BufferedReader(new InputStreamReader(p.getErrorStream(), StandardCharsets.UTF_8));

            //Writing to process stdIn
            OutputStream processInput = p.getOutputStream();
            bufferedProcessInput = new BufferedWriter(new OutputStreamWriter(processInput, StandardCharsets.UTF_8));
            String line;
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
            while ((line = standardOut.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            while ((line = standardErr.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            this.exitCode = p.waitFor();
            return new StringReader(sb.toString());
        } catch (IOException | InterruptedException e) {
            throw new CLIException("Exception happened while executing command", e);
        } finally {
            if (bufferedProcessInput != null) {
                try {
                    bufferedProcessInput.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (standardOut != null) {
                try {
                    standardOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (standardErr != null) {
                try {
                    standardErr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
