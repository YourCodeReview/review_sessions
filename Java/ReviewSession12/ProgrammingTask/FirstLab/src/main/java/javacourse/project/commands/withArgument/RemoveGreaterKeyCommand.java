package javacourse.project.commands.withArgument;



import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class RemoveGreaterKeyCommand extends Command {
    private StorageService storageService;

    public RemoveGreaterKeyCommand(StorageService storageService) {
        super("remove_greater_key",
                ": удаляет из коллекции все элементы, ключ которых превышает заданный",
                1);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        long key = Long.parseLong(argument[0]);
        storageService.removeGreaterKey(key);
        consoleManager.write("Элементы, ключи которых больше заданного удалены");
    }
}