package javacourse.project.commands.withArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.AskerArgument;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.data.Person;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class InsertCommand extends Command {
    private StorageService storageService;
    private AskerArgument asker;
    public InsertCommand(StorageService storageService, AskerArgument askerArgument) {
        super("insert",
                ": добавляет новый элемент с заданны ключом",
                1);
        this.storageService = storageService;
        this.asker = askerArgument;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        long key = Long.parseLong(argument[0]);
        if (storageService.checkKey(key)){
            Person person = asker.readPerson(consoleManager);
            storageService.insertKey(key, person);
            consoleManager.write("Элемент успешно добавлен в коллекцию");
        }else {
            consoleManager.write("Такой элемент уже существует");
        }

    }
}