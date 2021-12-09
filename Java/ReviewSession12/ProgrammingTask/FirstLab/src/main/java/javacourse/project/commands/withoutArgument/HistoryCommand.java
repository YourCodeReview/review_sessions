package javacourse.project.commands.withoutArgument;

import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.commands.CommandManager;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class HistoryCommand extends Command {
    private StorageService storageService;

    public HistoryCommand(StorageService storageService) {
        super("history",
                ": выводите последние 15 команд (без их аргументов)",
                0);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        CommandManager.getCommandList().forEach(System.out::println);
    }
}