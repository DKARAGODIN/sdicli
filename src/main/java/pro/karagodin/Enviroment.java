package pro.karagodin;

import java.util.Map;

public class Enviroment {

    private static final Map<String, String> ENVIRONMENT_VARIABLES = System.getenv();

    public static String getVariableValue(String var) {
        return ENVIRONMENT_VARIABLES.get(var);
    }

    public static void setVariable(String var, String value) {
        ENVIRONMENT_VARIABLES.put(var, value);
    }
}
