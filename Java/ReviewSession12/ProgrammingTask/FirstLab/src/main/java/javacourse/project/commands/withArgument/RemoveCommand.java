package javacourse.project.commands.withArgument;




import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class RemoveCommand extends Command {
    private StorageService storageService;

    public RemoveCommand(StorageService storageService) {
        super("remove",": удалить элемент из коллекции по его ключу",1);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        long key = Long.parseLong(argument[0]);
        storageService.removeKey(key);
        consoleManager.write("Элемент с ключом " + key + " удален");
    }
}