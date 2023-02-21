package pro.karagodin;

import java.util.Dictionary;

public class Enviroment {
    public static String getVariableValue(String var) {
        return vars.get(var);
    }

    public static void setVariable(String var, String value) {
        vars.put(var, value);
    }

    static protected Dictionary<String, String> vars;
}
