package javacourse.project.commands.withoutArgument;

import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class ClearCommand extends Command {
    private StorageService storageService;

    public ClearCommand(StorageService storageService) {
        super("clear",
                ": очистить коллекцию",
                0
                );
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        storageService.clear();
        consoleManager.write("Коллекция очищена");
    }
}
