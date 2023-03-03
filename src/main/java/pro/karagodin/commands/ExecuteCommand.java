package pro.karagodin.commands;

import pro.karagodin.exceptions.CLIException;

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

public class ExecuteCommand extends Command {

    public static final Map<String, String> ENVIRONMENT_VARIABLES = System.getenv();

    /**
     * TODO Rewrite with multithreading, cleaner resources handling
     * @param reader
     * @return
     * @throws CLIException
     */

    @Override
    public Reader run(Reader reader) throws CLIException {
        if (arguments.isEmpty()) {
            CLIException e = new CLIException("exec command must have at least one argument");
            e.setNeedToPrintStackTrace(false);
            throw e;
        }
        Runtime runtime = Runtime.getRuntime();
        BufferedWriter bufferedProcessInput = null;
        BufferedReader standardOut = null;
        BufferedReader standardErr = null;
        try {
            Process p = runtime.exec(arguments.toArray(String[]::new), getEnvp());
            standardOut = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            standardErr = new BufferedReader(new InputStreamReader(p.getErrorStream(), StandardCharsets.UTF_8));
            OutputStream processInput = p.getOutputStream();

            //Writing to process stdIn
            bufferedProcessInput = new BufferedWriter(new OutputStreamWriter(processInput, StandardCharsets.UTF_8));
            String line = null;
            if (reader != null) {
                BufferedReader inputReader = new BufferedReader(reader);
                while ((line = inputReader.readLine()) != null) {
                    bufferedProcessInput.write(line);
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
            return new StringReader(sb.toString());
        } catch (Exception e) {
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
        String[] envp = new String[ENVIRONMENT_VARIABLES.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : ENVIRONMENT_VARIABLES.entrySet()) {
            envp[i] = entry.getKey() + "=" + entry.getValue();
            i++;
        }
        return envp;
    }
}
