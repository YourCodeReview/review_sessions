package animals.userinterface;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class TextInterface extends LanguageRules {
    protected static final System.Logger LOGGER = System.getLogger("");

    private static final Pattern MESSAGE_DELIMITER = Pattern.compile("\\f");
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    protected final ResourceBundle resourceBundle;

    public TextInterface() {
        this(ResourceBundle.getBundle("messages"));
    }

    public TextInterface(final ResourceBundle bundle) {
        this.resourceBundle = bundle;
    }

    private static String pickMessage(final String[] messages) {
        return messages[random.nextInt(messages.length)];
    }

    public static String capitalize(final String data) {
        return data.substring(0, 1).toUpperCase() + data.substring(1).toLowerCase();
    }

    public void println() {
        System.out.println();
    }

    public void println(final String key, final Object... args) {
        this.print(key, args);
        System.out.println();
    }

    public void print(final String key, final Object... args) {
        System.out.print(MessageFormat.format(getText(key), args));
    }

    public void printf(final String key, final Object... args) {
        System.out.printf(resourceBundle.getString(key), args);
    }

    public String ask(final String key, final Object... args) {
        while (true) {
            println(key + ".prompt", args);
            final var answer = readToLowerCase();
            if (is(key, answer)) {
                return applyRules(key, answer);
            }
            println(key + ".error");
        }
    }

    public boolean askYesNo(final String key, final Object... args) {
        println(key, args);
        while (true) {
            final var response = readToLowerCase();
            if (is("positiveAnswer", response)) {
                return true;
            }
            if (is("negativeAnswer", response)) {
                return false;
            }
            println("ask.again");
        }
    }

    private String getText(final String key) {
        if (isNull(resourceBundle) || !resourceBundle.containsKey(key)) {
            return key;
        }
        if (resourceBundle.getObject(key) instanceof String[]) {
            return pickMessage(resourceBundle.getStringArray(key));
        }
        return pickMessage(MESSAGE_DELIMITER.split(resourceBundle.getString(key)));
    }

    public String readToLowerCase() {
        return scanner.nextLine().toLowerCase().trim();
    }

    public void printConditional(final String messageName) {
        LOGGER.log(System.Logger.Level.TRACE, "printConditional: {0}", messageName);
        final var messages = new ArrayList<String>();
        final var time = LocalTime.now();
        final var date = LocalDate.now();

        final Map<String, Predicate<String>> conditions = Map.of(
                "time.after", startTime -> time.isAfter(LocalTime.parse(startTime)),
                "time.before", endTime -> time.isBefore(LocalTime.parse(endTime)),
                "date.after", startDate -> date.isAfter(LocalDate.parse(date.getYear() + "-" + startDate)),
                "date.before", endDate -> date.isBefore(LocalDate.parse(date.getYear() + "-" + endDate)),
                "date.equals", someDate -> date.equals(LocalDate.parse(date.getYear() + "-" + someDate)));

        final Predicate<String> isConditionalMessage = key -> key.startsWith(messageName + ".");

        final Predicate<String> isConditionActual = message -> {
            final var period = message.substring(messageName.length() + 1) + ".";
            return conditions.entrySet().stream()
                    .filter(entry -> resourceBundle.containsKey(period + entry.getKey()))
                    .allMatch(entry -> entry.getValue().test(resourceBundle.getString(period + entry.getKey())));
        };
        final Function<String, List<String>> splitMessage = key -> Arrays.asList(
                MESSAGE_DELIMITER.split(resourceBundle.getString(key)));

        resourceBundle.keySet().stream()
                .filter(isConditionalMessage)
                .filter(isConditionActual)
                .map(splitMessage)
                .forEach(messages::addAll);

        if (resourceBundle.containsKey(messageName)) {
            messages.addAll(splitMessage.apply(messageName));
        }

        LOGGER.log(System.Logger.Level.TRACE, "exiting printConditional: {0}, {1}", messageName, messages);
    }

}