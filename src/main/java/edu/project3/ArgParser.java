package edu.project3;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

// Simple arg parser for arguments with values
public class ArgParser {
    private final Map<String, Argument> definedArgs;

    public ArgParser() {
        definedArgs = new HashMap<>();
    }

    public ArgParser requiredArgument(@NotNull String name) {
        definedArgs.put(name, new Argument(true, null));
        return this;
    }

    public ArgParser optionalArgument(@NotNull String name, String defaultValue) {
        definedArgs.put(name, new Argument(false, defaultValue));
        return this;
    }

    @NotNull
    public Map<String, String> parse(String[] args) throws ValidationException {
        Map<String, String> parsedArgs = new HashMap<>();

        for (int i = 0; i < args.length; i += 2) {
            if (!args[i].startsWith("--") || !definedArgs.containsKey(args[i].substring(2))) {
                throw new ValidationException("Unexpected argument '%s' was provided".formatted(args[i]));
            } else if (i == args.length - 1) {
                throw new ValidationException("No value was provided for argument '%s'".formatted(args[i]));
            }
            parsedArgs.put(args[i].substring(2), args[i + 1]);
        }

        for (var entry : definedArgs.entrySet()) {
            if (entry.getValue().isRequired && !parsedArgs.containsKey(entry.getKey())) {
                throw new ValidationException(
                    "No value was provided for required argument '%s'".formatted(entry.getKey())
                );
            } else if (!entry.getValue().isRequired && !parsedArgs.containsKey(entry.getKey())) {
                parsedArgs.put(entry.getKey(), entry.getValue().defaultValue);
            }
        }

        return parsedArgs;
    }

    private record Argument(boolean isRequired, String defaultValue) {
    }
}
