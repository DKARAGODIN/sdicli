package pro.karagodin;

import java.io.Reader;

import pro.karagodin.exceptions.CLIException;

public class Interpreter {
    private final Runner runner = new Runner();
    private final Scanner scanner = new Scanner();
    private final Parser parser = new Parser();


    public Reader interpret(String line) throws CLIException {
        var lexemes = scanner.scan(line);
        var commands = parser.parse(lexemes);
        return runner.run(commands);
    }
}
