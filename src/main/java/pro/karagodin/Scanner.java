package pro.karagodin;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import pro.karagodin.exceptions.CLIException;

public class Scanner {

    private static final Map<String, LexemeType> STR_TO_LEXEME_TYPE = Map.ofEntries(
            entry("eq", LexemeType.ASSIGN),
            entry("dq", LexemeType.DQ),
            entry("sq", LexemeType.SQ),
            entry("pipe", LexemeType.PIPE),
            entry("space", LexemeType.SPACE),
            entry("str", LexemeType.STR));

    public List<Lexeme> scan(String str) throws CLIException {
        var strRegex = "(?<str>[^=\\|\\s'\"]+)";
        var equalRegex = "(?<eq>=)";
        var pipeRegex = "(?<pipe>\\|)";
        var dqRegex = "\"(?<dq>[^\"]+)\"";
        var sqRegex = "'(?<sq>[^']+)'";
        var spaceRegex = "(?<space>[\\s]+)";
        var fullRegex = "^" + strRegex + "|" + equalRegex + "|" + pipeRegex + "|" + dqRegex + "|" + sqRegex + "|"
                + spaceRegex;

        return scan(str, fullRegex, STR_TO_LEXEME_TYPE.keySet());

    }

    public static List<Lexeme> scan(String text, String regex, Collection<String> groupsNames) throws CLIException {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(text);
        var lexemes = new ArrayList<Lexeme>();
        while (matcher.find()) {
            var groupName = groupsNames.stream().filter(name -> matcher.group(name) != null).findFirst();
            groupName.ifPresent(s -> lexemes.add(new Lexeme(matcher.group(s), STR_TO_LEXEME_TYPE.get(s))));
            matcher.region(matcher.end(), text.length());
        }

        if (matcher.regionStart() != matcher.regionEnd()) {
            throw new CLIException("Parse error at pos " + matcher.regionStart());
        }
        return lexemes;
    }
}
