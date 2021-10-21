import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.stream.Stream;

public class GuessAnimalTest extends StageTest<String> {

    String[] languages = new String[]{"en", "eo"};

    @DynamicTest(data = "languages")
    CheckResult testLanguages(final String language) throws IOException {
        final var fileName = "animals" + ("en".equals(language) ? "." : "_" + language + ".");

        final var locale = Locale.getDefault();
        final var systemLanguage = System.getProperty("user.language");
        Locale.setDefault(new Locale(language));
        System.setProperty("user.language", language);

        deleteFiles(fileName);
        final var result = new Scenario(language).check();
        deleteFiles(fileName);

        Locale.setDefault(locale);
        System.setProperty("user.language", systemLanguage);

        return result;
    }

    private void deleteFiles(String fileName) {
        Stream.of("yaml", "json", "xml")
                .map(fileName::concat)
                .map(File::new)
                .filter(File::exists)
                .forEach(File::delete);
    }
}