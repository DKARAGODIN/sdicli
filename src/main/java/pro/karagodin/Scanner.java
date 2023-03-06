package pro.karagodin;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.Collection;

public class Scanner {
    public List<Lexeme> scan(String str) {
        var strRegex = "(?<str>[^=\\|\\s\'\"]+)";
        var equalRegex = "(?<eq>=)";
        var pipeRegex = "(?<pipe>\\|)";
        var dqRegex = "\"(?<dq>[^\"]+)\"";
        var sqRegex = "'(?<sq>[^']+)'";
        var spaceRegex = "(?<space>[\\s]+)";
        var fullRegex = "^" + strRegex + "|" + equalRegex + "|" + pipeRegex + "|" + dqRegex + "|" + sqRegex + "|"
                + spaceRegex;

        return scan(str, fullRegex, strToLexemeType.keySet());

    }

    public static List<Lexeme> scan(String text, String regex, Collection<String> groupsNames) {
        System.out.println("regex: " + regex);
        System.out.println("text: " + text);
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(text);
        var lexemes = new ArrayList<Lexeme>();
        while (matcher.find()) {
            for (var groupName : groupsNames)
                if (matcher.group(groupName) != null) {
                    lexemes.add(new Lexeme(matcher.group(groupName), strToLexemeType.get(groupName)));
                    System.out.println(groupName + ": " + matcher.group(groupName));
                    break;

                }
            matcher.region(matcher.end(), text.length());
        }

        if (matcher.regionStart() != matcher.regionEnd()) {
            throw new IllegalArgumentException("Parse error at pos " + matcher.regionStart());
        }
        return lexemes;
    }

    private static Map<String, LexemeType> strToLexemeType = Map.ofEntries(
            entry("eq", LexemeType.ASSIGN),
            entry("dq", LexemeType.DQ),
            entry("sq", LexemeType.SQ),
            entry("pipe", LexemeType.PIPE),
            entry("space", LexemeType.SPACE),
            entry("str", LexemeType.STR));

}
