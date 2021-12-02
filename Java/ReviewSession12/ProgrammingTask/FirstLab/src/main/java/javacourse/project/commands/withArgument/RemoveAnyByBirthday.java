package javacourse.project.commands.withArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RemoveAnyByBirthday extends Command {
    private StorageService storageService;

    public RemoveAnyByBirthday(StorageService storageService) {
        super("remove_any_by_birthday",
                ": удаляет из коллекции один элемент, значение поля birthday которого эквивалентно заданному",
                1);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        LocalDateTime localDateTime = LocalDate.parse(argument[0], DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
        storageService.removeAnyByBirthday(localDateTime);
        consoleManager.write("элементы коллекции, значение birthday которых равен " + localDateTime.toString() + " удалены");
    }
}