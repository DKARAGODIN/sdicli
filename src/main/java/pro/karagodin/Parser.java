package pro.karagodin;

import java.util.ArrayList;
import java.util.List;

import pro.karagodin.commands.*;
import pro.karagodin.exceptions.CLIException;

public class Parser {
    public List<Command> parse(List<Lexeme> lexemes) throws CLIException {
        var cmdAndArgs = parseCommandAndArguments(lexemes);
        var cmd = getCommandByName(cmdAndArgs.get(0));
        cmd.setArguments(cmdAndArgs.subList(1, cmdAndArgs.size()));
        return List.of(cmd);
    }

    private List<String> parseCommandAndArguments(List<Lexeme> lexemes) {
        List<String> args = new ArrayList<String>();
        var argBuilder = new StringBuilder();
        int i = 0;
        while (i < lexemes.size() && lexemes.get(i).type == LexemeType.SPACE) {
            i++;
        }
        while (i < lexemes.size()) {
            while (i < lexemes.size() && lexemes.get(i).type != LexemeType.SPACE) {
                argBuilder.append(lexemes.get(i).view);
                i++;
            }
            args.add(argBuilder.toString());
            argBuilder.setLength(0);
            while (i < lexemes.size() && lexemes.get(i).type == LexemeType.SPACE) {
                i++;
            }
        }
        return args;
    }

    private Command getCommandByName(String cmdName) throws CLIException {
        switch (cmdName) {
            case "cat":
                return new CatCommand();
            case "exec":
                return new ExecuteCommand();
            case "wc":
                return new WcCommand();
            case "pwd":
                return new PwdCommand();
            case "exit":
                return new ExitCommand();
            case "echo":
                return new EchoCommand();
            default:
                CLIException e = new CLIException("Not known command: " + cmdName);
                e.setNeedToPrintStackTrace(false);
                throw e;
        }
    }
}
