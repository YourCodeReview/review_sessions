package animals;

import animals.repository.StorageService;
import animals.userinterface.Application;

import static java.lang.System.Logger.Level.TRACE;

public final class Main {
    private static final System.Logger LOGGER = System.getLogger("");

    public static void main(String[] args) {
        LOGGER.log(TRACE, args);

        final var isTypeSpecified = args.length > 1 && args[0].equals("-type");
        final var storageService = isTypeSpecified
                ? StorageService.of(args[1])
                : StorageService.getDefaultService();

        new Application(storageService).run();

        LOGGER.log(TRACE, "finished");
    }
}