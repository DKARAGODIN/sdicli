package pro.karagodin;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import pro.karagodin.commands.CatCommand;
import pro.karagodin.commands.Command;
import pro.karagodin.commands.EchoCommand;
import pro.karagodin.commands.ExecuteCommand;
import pro.karagodin.commands.ExitCommand;
import pro.karagodin.commands.GrepCommand;
import pro.karagodin.commands.PwdCommand;
import pro.karagodin.commands.WcCommand;
import pro.karagodin.exceptions.CLIException;

public class Parser {

    private static final Map<String, Function<String, Command>> STR_TO_CMD_FACTORY = Map.ofEntries(
            entry("cat", c -> new CatCommand()),
            entry("wc", c -> new WcCommand()),
            entry("pwd", c -> new PwdCommand()),
            entry("exit", c -> new ExitCommand()),
            entry("echo", c -> new EchoCommand()),
            entry("grep", c -> new GrepCommand()));

    private static final String VARIABLE_REGEX = "[a-zA-Z_]\\w*";

    public List<Command> parse(List<Lexeme> lexemes) throws CLIException {
        if (isAssignVariable(lexemes)) {
            parseAssignVariable(lexemes);
            return new ArrayList<>();
        }
        var cmdsLexemes = splitByPipe(lexemes);
        var cmds = new ArrayList<Command>();
        for (var cmdLexemes : cmdsLexemes)
            cmds.add(parseCommand(cmdLexemes));
        return cmds;
    }

    private String substitute(String text) {
        var pattern = Pattern.compile("\\$(" + VARIABLE_REGEX + ")");
        var matcher = pattern.matcher(text);
        var replacedLine = new StringBuilder();
        while (matcher.find())
            matcher.appendReplacement(replacedLine, Environment.getVariableValue(matcher.group(1)));
        matcher.appendTail(replacedLine);
        return replacedLine.toString();
    }

    private Lexeme substitute(Lexeme lex) {
        if (lex.type() == LexemeType.STR || lex.type() == LexemeType.DQ)
            return new Lexeme(substitute(lex.view()), lex.type());
        return lex;
    }

    private boolean isAssignVariable(List<Lexeme> lexemes) {
        return lexemes.size() >= 2
                && lexemes.get(0).type() == LexemeType.STR
                && lexemes.get(1).type() == LexemeType.ASSIGN
                && isVariable(lexemes.get(0).view());
    }

    private boolean isVariable(String str) {
        return str.matches(VARIABLE_REGEX);
    }

    private void parseAssignVariable(List<Lexeme> lexemes) {
        Environment.setVariable(lexemes.get(0).view(), parseVariableValue(lexemes));
    }

    private String parseVariableValue(List<Lexeme> lexemes) {
        return lexemes.stream().skip(2).map(this::substitute).map(Lexeme::view).collect(Collectors.joining());
    }

    private Command parseCommand(List<Lexeme> cmdLexemes) throws CLIException {
        var cmdAndArgs = parseCommandAndArguments(cmdLexemes);
        if (cmdAndArgs.isEmpty())
            throw new CLIException("Empty command");
        var cmd = getCommandByName(cmdAndArgs.get(0));
        cmd.setArguments(cmdAndArgs.subList(1, cmdAndArgs.size()));
        return cmd;
    }

    private List<List<Lexeme>> splitByPipe(List<Lexeme> lexemes) {
        var pipesIndexes = IntStream.range(0, lexemes.size())
                .filter(i -> lexemes.get(i).type() == LexemeType.PIPE)
                .boxed().toList();
        var cmds = new ArrayList<>(IntStream.range(0, pipesIndexes.size())
                .mapToObj(i -> lexemes.subList(i > 0 ? pipesIndexes.get(i - 1) + 1 : 0, pipesIndexes.get(i)))
                .toList());
        var lastCmdStart = pipesIndexes.size() > 0 ? pipesIndexes.get(pipesIndexes.size() - 1) + 1 : 0;
        cmds.add(lexemes.subList(lastCmdStart, lexemes.size()));
        return cmds;
    }

    private List<String> parseCommandAndArguments(List<Lexeme> lexemes) {
        List<String> args = new ArrayList<>();
        int i = skipSpaces(0, lexemes);
        while (i < lexemes.size()) {
            var argComps = getCmdOrArgComponents(i, lexemes);
            i += argComps.size();
            args.add(String.join("", argComps));
            i = skipSpaces(i, lexemes);
        }
        return args;
    }


    private int skipSpaces(int startIndex, List<Lexeme> lexemes) {
        return (int) (startIndex + lexemes.stream()
                .skip(startIndex)
                .takeWhile(l -> l.type() == LexemeType.SPACE)
                .count());
    }

    private List<String> getCmdOrArgComponents(int startIndex, List<Lexeme> lexemes) {
        return lexemes.stream()
                .skip(startIndex)
                .takeWhile(lex -> lex.type() != LexemeType.SPACE)
                .map(this::substitute)
                .map(Lexeme::view).toList();
    }

    private Command getCommandByName(String cmdName) {
        if (!STR_TO_CMD_FACTORY.containsKey(cmdName)) {
            return new ExecuteCommand(cmdName);
        }
        return STR_TO_CMD_FACTORY.get(cmdName).apply(cmdName);

    }
}
