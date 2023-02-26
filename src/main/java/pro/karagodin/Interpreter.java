package pro.karagodin;

import pro.karagodin.commands.Command;
import pro.karagodin.commands.EchoCommand;
import pro.karagodin.commands.ExitCommand;
import pro.karagodin.commands.PwdCommand;
import pro.karagodin.commands.WcCommand;
import pro.karagodin.exceptions.CLIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Interpreter {
    /**
     * Simple first implementations to show and test functionality
     * TODO Add parsing. Improve reading.
     * @throws IOException
     */
    public void start() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Runner runner = new Runner();
        while(true) {
            System.out.printf("> ");
            String line = bufferedReader.readLine();
            String[] parsed = line.split(" ");
            List<Command> commands = new ArrayList<>();
            switch (parsed[0]) {
                case "wc":
                    Command wc = new WcCommand();
                    fillArguments(wc, parsed);
                    commands.add(wc);
                    break;
                case "pwd":
                    commands.add(new PwdCommand());
                    break;
                case "exit" :
                    commands.add(new ExitCommand());
                    break;
                case "echo" :
                    Command echo = new EchoCommand();
                    fillArguments(echo, parsed);
                    commands.add(echo);
                    break;
                default:
                    System.out.println("Invalid command");
                    continue;
            }
            try {
                Reader reader = runner.run(commands);
                BufferedReader resultReader = new BufferedReader(reader);
                String strCurrentLine;
                while ((strCurrentLine = resultReader.readLine()) != null) {
                    System.out.println(strCurrentLine);
                }
            } catch (CLIException e) {
                System.out.println(e.getMessage());
                if (e.isNeedToPrintStackTrace()) {
                    e.printStackTrace(System.out);
                }
                if (e.isExit()) {
                    System.exit(e.getStatus_code());
                }
            }
        }
    }

    private void fillArguments(Command command, String[] parsed) {
        List<String> args = new ArrayList<>(parsed.length);
        for (int i = 1; i < parsed.length; i++) {
            args.add(parsed[i]);
        }
        command.setArguments(args);

    }

    public static void main(String[] args) throws IOException {
        Interpreter interpreter = new Interpreter();
        interpreter.start();
    }
}
