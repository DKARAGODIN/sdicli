package pro.karagodin;

import pro.karagodin.exceptions.CLIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CLI {
    public void start() throws IOException {
        var interpreter = new Interpreter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        while (true) {
            System.out.print("> ");
            String line = bufferedReader.readLine();
            if (line == null)
                break;
            if(line.trim().isEmpty())
                continue;

            try {
                try (BufferedReader resultReader = new BufferedReader(interpreter.interpret(line))) {
                    resultReader.lines().forEach(System.out::println);
                }
            } catch (CLIException e) {
                System.out.println(e.getMessage());
                if (e.isNeedToPrintStackTrace()) {
                    e.printStackTrace(System.out);
                }
                if (e.isExit()) {
                    exit(e);
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        var CLI = new CLI();
        CLI.start();
    }

    public void exit(CLIException e) {
        System.exit(e.getStatusCode());
    }
}
