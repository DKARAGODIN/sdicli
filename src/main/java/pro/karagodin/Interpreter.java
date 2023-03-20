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
        while (true) {
            System.out.print("> ");
            String line = bufferedReader.readLine();
            if (line == null)
                break;
            if ("".equals(line))
                continue;

            try {
                var lexemes = Scanner.scan(line);
                var commands = Parser.parse(lexemes);
                Reader reader = Runner.run(commands);

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
