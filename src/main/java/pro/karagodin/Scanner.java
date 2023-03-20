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

    private Pattern pattern = Pattern.compile(getFullRegex());

    public List<Lexeme> scan(String str) throws CLIException {
        return scan(str, STR_TO_LEXEME_TYPE.keySet());
    }

    private String getFullRegex() {
        var strRegex = "(?<str>[^=\\|\\s\'\"]+)";
        var equalRegex = "(?<eq>=)";
        var pipeRegex = "(?<pipe>\\|)";
        var dqRegex = "\"(?<dq>[^\"]+)\"";
        var sqRegex = "'(?<sq>[^']+)'";
        var spaceRegex = "(?<space>[\\s]+)";
        var fullRegex = "^" + strRegex + "|" + equalRegex + "|" + pipeRegex + "|" + dqRegex + "|" + sqRegex + "|"
                + spaceRegex;
        return fullRegex;
    }

    private List<Lexeme> scan(String text, Collection<String> groupsNames) throws CLIException {
        var matcher = pattern.matcher(text);
        var lexemes = new ArrayList<Lexeme>();
        while (matcher.find()) {
            for (var groupName : groupsNames)
                if (matcher.group(groupName) != null) {
                    lexemes.add(new Lexeme(matcher.group(groupName), STR_TO_LEXEME_TYPE.get(groupName)));
                    break;

                }
            matcher.region(matcher.end(), text.length());
        }

        if (matcher.regionStart() != matcher.regionEnd()) {
            throw new CLIException("Parse error at pos " + matcher.regionStart());
        }
        return lexemes;
    }
}
