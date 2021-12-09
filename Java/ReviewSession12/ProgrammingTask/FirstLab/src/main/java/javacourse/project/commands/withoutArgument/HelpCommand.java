package javacourse.project.commands.withoutArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.commands.CommandManager;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class HelpCommand extends Command {
    private StorageService storageService;

    public HelpCommand(StorageService storageService) {
        super("help",
                ": вывести справку по доступным командам",
                0);
        this.storageService = storageService;
    }


    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        CommandManager.getAllCommand().forEach(x->{
            System.out.println(x.getName() + x.getDescription());
        });
    }
}