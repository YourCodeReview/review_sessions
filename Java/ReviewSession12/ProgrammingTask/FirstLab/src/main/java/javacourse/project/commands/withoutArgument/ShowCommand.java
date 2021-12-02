package javacourse.project.commands.withoutArgument;

import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class ShowCommand extends Command {
    private StorageService storageService;

    public ShowCommand(StorageService storageService) {
        super("show",
                ": выводит в стандартный поток вывода все элементы коллекции в строковом представлении",
                0
                );
        this.storageService = storageService;

    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        storageService.show();

    }
}