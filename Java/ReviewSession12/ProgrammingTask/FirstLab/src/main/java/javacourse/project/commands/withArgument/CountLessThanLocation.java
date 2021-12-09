package javacourse.project.commands.withArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.AskerArgument;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.data.Location;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class CountLessThanLocation extends Command {
    private StorageService storageService;
    private AskerArgument asker;
    public CountLessThanLocation(StorageService storageService, AskerArgument askerArgument) {
        super("count_less_than_location",": выводит количество элементов, значение поля location которых меньше заданного", 0);
        this.storageService = storageService;
        this.asker = askerArgument;

    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        Location location = asker.readLocation(consoleManager);
        consoleManager.write("количество элементов, значение поля location которых меньше заданного : " + storageService.countLessThanLocation(location));
    }
}