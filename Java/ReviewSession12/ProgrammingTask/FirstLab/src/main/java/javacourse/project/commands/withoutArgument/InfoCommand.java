package javacourse.project.commands.withoutArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class InfoCommand extends Command {
    private StorageService storageService;

    public InfoCommand(StorageService storageService) {
        super("info",
                ": выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)",
                0);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        storageService.info();
    }
}