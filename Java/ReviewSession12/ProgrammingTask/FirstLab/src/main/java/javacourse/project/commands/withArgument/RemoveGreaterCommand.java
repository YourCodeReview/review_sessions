package javacourse.project.commands.withArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.AskerArgument;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.data.Person;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class RemoveGreaterCommand extends Command {
    private StorageService storageService;
    private AskerArgument asker;
    public RemoveGreaterCommand(StorageService storageService, AskerArgument askerArgument) {
        super("remove_greater",
                ": удаляет все элементы из коллекции, превышающие заданный",
                0);
        this.storageService = storageService;
        this.asker = askerArgument;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        Person person = asker.readPerson(consoleManager);
        storageService.removeGreater(person);
        consoleManager.write("Элементы, превышающие заданный удалены");
    }
}