package pro.karagodin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import pro.karagodin.commands.CatCommand;
import pro.karagodin.commands.Command;
import pro.karagodin.commands.EchoCommand;
import pro.karagodin.commands.ExecuteCommand;
import pro.karagodin.commands.ExitCommand;
import pro.karagodin.commands.PwdCommand;
import pro.karagodin.commands.WcCommand;
import pro.karagodin.exceptions.CLIException;

import static java.util.Map.entry;

public class Parser {

    private static final Map<String, Function<String, Command>> STR_TO_CMD_FACTORY = Map.ofEntries(
            entry("cat", c -> new CatCommand()),
            entry("exec", ExecuteCommand::new),
            entry("wc", c -> new WcCommand()),
            entry("pwd", c -> new PwdCommand()),
            entry("exit", c -> new ExitCommand()),
            entry("echo", c -> new EchoCommand()));

    public List<Command> parse(List<Lexeme> lexemes) throws CLIException {
        var cmdsLexemes = splitByPipe(lexemes);
        var cmds = new ArrayList<Command>();
        for(var cmdLexemes: cmdsLexemes)
            cmds.add(parseCommand(cmdLexemes));
        return cmds;
    }

    private Command parseCommand(List<Lexeme> cmdLexemes) throws CLIException {
        var cmdAndArgs = parseCommandAndArguments(cmdLexemes);
        var cmd = getCommandByName(cmdAndArgs.get(0));
        cmd.setArguments(cmdAndArgs.subList(1, cmdAndArgs.size()));
        return cmd;
    }

    private List<List<Lexeme>> splitByPipe(List<Lexeme> lexemes){
        var pipesIndexes = IntStream.range(0, lexemes.size())
                .filter(i -> lexemes.get(i).getType() == LexemeType.PIPE)
                .boxed().toList();
        var cmds = new ArrayList<>(IntStream.range(0, pipesIndexes.size())
                .mapToObj(i -> lexemes.subList(i > 0 ? pipesIndexes.get(i - 1) + 1 : 0, pipesIndexes.get(i)))
                .toList());
        var lastCmdStart = pipesIndexes.size() > 0 ? pipesIndexes.get(pipesIndexes.size()-1) + 1 : 0;
        cmds.add(lexemes.subList(lastCmdStart, lexemes.size()));
        return cmds;
    }

    private List<String> parseCommandAndArguments(List<Lexeme> lexemes) {
        List<String> args = new ArrayList<>();
        int i = skipSpaces(0, lexemes);
        while (i < lexemes.size()) {
            var argComps = getArgsComponents(i, lexemes);
            i += argComps.size();
            args.add(String.join("", argComps));
            i = skipSpaces(i, lexemes);
        }
        return args;
    }


    private int skipSpaces(int startIndex, List<Lexeme> lexemes) {
        return (int) (startIndex + lexemes.stream()
                .skip(startIndex)
                .takeWhile(l -> l.getType() == LexemeType.SPACE)
                .count());
    }

    private List<String> getArgsComponents(int startIndex, List<Lexeme> lexemes) {
        return lexemes.stream()
                .skip(startIndex)
                .takeWhile(lex -> lex.getType() != LexemeType.SPACE)
                .map(Lexeme::getView).toList();
    }

    private Command getCommandByName(String cmdName) throws CLIException {
        if (!STR_TO_CMD_FACTORY.containsKey(cmdName)) {
            CLIException e = new CLIException("Not known command: " + cmdName);
            e.setNeedToPrintStackTrace(false);
            throw e;
        }
        return STR_TO_CMD_FACTORY.get(cmdName).apply("booba");

    }
}
