package pro.karagodin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import pro.karagodin.exceptions.CLIException;

public class Interpreter {

    public void start() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        Runner runner = new Runner();
        var scanner = new Scanner();
        var parser = new Parser();

        while (true) {
            System.out.printf("> ");
            String line = bufferedReader.readLine();
            if ("".equals(line))
                continue;

            try {
                var lexemes = scanner.scan(line);
                var commands = parser.parse(lexemes);
                Reader reader = runner.run(commands);

                try (BufferedReader resultReader = new BufferedReader(reader)) {
                    String strCurrentLine;
                    while ((strCurrentLine = resultReader.readLine()) != null) {
                        System.out.println(strCurrentLine);
                    }
                }
            } catch (CLIException e) {
                System.out.println(e.getMessage());
                if (e.isNeedToPrintStackTrace()) {
                    e.printStackTrace(System.out);
                }
                if (e.isExit()) {
                    System.exit(e.getStatusCode());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.start();
    }
}
