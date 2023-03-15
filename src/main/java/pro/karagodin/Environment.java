package pro.karagodin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Environment {

    private static final Map<String, String> ENVIRONMENT_VARIABLES = new HashMap<>(System.getenv());

    public static String getVariableValue(String var) {
        return ENVIRONMENT_VARIABLES.get(var);
    }

    public static void setVariable(String var, String value) {
        ENVIRONMENT_VARIABLES.put(var, value);
    }

    public static int getEnvironmentSize() {
        return ENVIRONMENT_VARIABLES.size();
    }

    public static Set<Map.Entry<String, String>> getEntriesSet() {
        return ENVIRONMENT_VARIABLES.entrySet();
    }
}