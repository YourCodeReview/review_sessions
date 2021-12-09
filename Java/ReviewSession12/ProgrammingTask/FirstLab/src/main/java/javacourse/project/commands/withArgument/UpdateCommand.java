package javacourse.project.commands.withArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.AskerArgument;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public class UpdateCommand extends Command {
    private StorageService storageService;
    private AskerArgument asker;
    public UpdateCommand(StorageService storageService, AskerArgument askerArgument) {
        super("update",
                ": обновляет значение элемента коллекции, id которого равен заданному",
                1);
        this.storageService = storageService;
        this.asker = askerArgument;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        long id = Long.parseLong(argument[0]);
        if (storageService.checkId(id)){
            asker.updatePerson(storageService.getPersonById(id),consoleManager);
            //storageService.updateID(id, person);
            consoleManager.write("Элемент успешно изменен");
        }else {
            System.out.println("Элемент с таким ID отсутсвует");
            //
        }
    }
}
