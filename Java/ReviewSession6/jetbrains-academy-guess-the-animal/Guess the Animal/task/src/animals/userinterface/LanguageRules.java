package animals.userinterface;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toUnmodifiableMap;

public abstract class LanguageRules {
    protected static final Map<String, Pattern> patterns;
    private static final ResourceBundle rules;

    static {
        rules = ResourceBundle.getBundle("patterns");
        patterns = rules.keySet().stream()
                .filter(key -> !key.endsWith(".replace"))
                .collect(toUnmodifiableMap(key -> key, key -> Pattern.compile(rules.getString(key))));
    }

    protected static String applyRules(final String rule, final String data) {
        for (int i = 1; ; i++) {
            final var key = rule + "." + i;
            final var pattern = patterns.get(key + ".pattern");

            if (isNull(pattern)) {
                return data;
            }
            final var matcher = pattern.matcher(data);
            if (matcher.matches()) {
                return matcher.replaceFirst(rules.getString(key + ".replace"));
            }
        }
    }

    protected boolean is(final String key, final String data) {
        return patterns.get(key + ".isCorrect").matcher(data).matches();
    }
}
