package pro.karagodin;

import static java.util.Map.entry;

import java.util.ArrayList;
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

    private static final Pattern pattern = Pattern.compile(getFullRegex());

    private static String getFullRegex() {
        var strRegex = "(?<str>[^=\\|\\s'\"]+)";
        var equalRegex = "(?<eq>=)";
        var pipeRegex = "(?<pipe>\\|)";
        var dqRegex = "\"(?<dq>[^\"]+)\"";
        var sqRegex = "'(?<sq>[^']+)'";
        var spaceRegex = "(?<space>[\\s]+)";
        return "^" + strRegex + "|" + equalRegex + "|" + pipeRegex + "|" + dqRegex + "|" + sqRegex + "|"
                + spaceRegex;
    }

    public static List<Lexeme> scan(String text) throws CLIException {
        var matcher = pattern.matcher(text);
        var lexemes = new ArrayList<Lexeme>();
        while (matcher.find()) {
            for (var entry : STR_TO_LEXEME_TYPE.entrySet()) {
                var groupName = entry.getKey();
                var lexType = entry.getValue();
                var matchedString = matcher.group(groupName);
                if (matchedString != null) {
                    lexemes.add(new Lexeme(matchedString, lexType));
                    break;
                }
            }
            matcher.region(matcher.end(), text.length());
        }

        if (matcher.regionStart() != matcher.regionEnd()) {
            throw new CLIException("Parse error at pos " + matcher.regionStart());
        }
        return lexemes;
    }
}
