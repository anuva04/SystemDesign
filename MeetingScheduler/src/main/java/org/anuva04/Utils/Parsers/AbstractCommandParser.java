package org.anuva04.Utils.Parsers;

import java.util.List;
import java.util.Map;

public abstract class AbstractCommandParser {
    public String validate(Map<String, String> args) {
        for(String arg : getRequiredArgs()) {
            if(!args.containsKey(arg)) return arg;
        }
        return null;
    }

    protected abstract List<String> getRequiredArgs();
    public abstract void callMethod(Map<String, String> args);
}
