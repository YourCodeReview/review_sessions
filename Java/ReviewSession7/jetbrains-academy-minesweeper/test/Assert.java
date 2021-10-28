import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;

public class Assert {
    private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

    static void that(
            final boolean trueCondition,
            final String errorMessage,
            final Object... args) {

        if (!trueCondition) {
            final var feedback = format(messages.getString(errorMessage), args);
            throw new WrongAnswer(feedback);
        }
    }

    static void contains(
            final String output,
            final String lookFor,
            final String errorMessage,
            final Object... args) {

        if (!output.toLowerCase().contains(lookFor)) {
            final var feedback = format(messages.getString(errorMessage), args)
                    + "\nExpected to find '" + lookFor + "' in output."
                    + "\nYou program output:\n" + output;
            throw new WrongAnswer(feedback);
        }
    }

    static void find(
            final String output,
            final String lookFor,
            final String errorMessage,
            final Object... args) {

        if (!Pattern.compile(lookFor).matcher(output).find()) {
            final var feedback = format(messages.getString(errorMessage), args)
                    + "\nExpected to matches pattern '" + lookFor + "' in output."
                    + "\nYou program output:\n" + output;
            throw new WrongAnswer(feedback);
        }
    }

    static void matches(
            final String output,
            final String lookFor,
            final String errorMessage,
            final Object... args) {

        if (!output.toLowerCase().matches(lookFor)) {
            final var feedback = format(messages.getString(errorMessage), args)
                    + "\nExpected to matches pattern '" + lookFor + "' in output."
                    + "\nYou program output:\n" + output;
            throw new WrongAnswer(feedback);
        }
    }

    static OutcomeError error(final String errorMessage, final Object... args) {
        return new WrongAnswer(format(messages.getString(errorMessage), args));
    }
}
