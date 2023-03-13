package pro.karagodin;

import java.util.ArrayList;
import java.util.List;

import pro.karagodin.commands.CatCommand;
import pro.karagodin.commands.Command;
import pro.karagodin.commands.EchoCommand;
import pro.karagodin.commands.ExecuteCommand;
import pro.karagodin.commands.ExitCommand;
import pro.karagodin.commands.PwdCommand;
import pro.karagodin.commands.WcCommand;
import pro.karagodin.exceptions.CLIException;

public class Parser {
    public List<Command> parse(List<Lexeme> lexemes) throws CLIException {
        var cmdAndArgs = parseCommandAndArguments(lexemes);
        var cmd = getCommandByName(cmdAndArgs.get(0));
        if (cmd instanceof ExecuteCommand)
            cmd.setArguments(cmdAndArgs);
        else
            cmd.setArguments(cmdAndArgs.subList(1, cmdAndArgs.size()));
        return List.of(cmd);
    }

    private List<String> parseCommandAndArguments(List<Lexeme> lexemes) {
        List<String> args = new ArrayList<>();
        var argBuilder = new StringBuilder();
        int i = 0;
        while (i < lexemes.size() && lexemes.get(i).getType() == LexemeType.SPACE) {
            i++;
        }
        while (i < lexemes.size()) {
            while (i < lexemes.size() && lexemes.get(i).getType() != LexemeType.SPACE) {
                argBuilder.append(lexemes.get(i).getView());
                i++;
            }
            args.add(argBuilder.toString());
            argBuilder.setLength(0);
            while (i < lexemes.size() && lexemes.get(i).getType() == LexemeType.SPACE) {
                i++;
            }
        }
        return args;
    }

    private Command getCommandByName(String cmdName) throws CLIException {
        switch (cmdName) {
            case "cat":
                return new CatCommand();
            case "wc":
                return new WcCommand();
            case "pwd":
                return new PwdCommand();
            case "exit":
                return new ExitCommand();
            case "echo":
                return new EchoCommand();
            default:
                return new ExecuteCommand();
        }
    }
}
