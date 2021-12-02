package javacourse.project.commands.withoutArgument;


import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;
import javacourse.project.fileWorkers.WriterFile;

public class SaveCommand extends Command {
    private  String address;
    private StorageService storageService;
    private String fileName;

    public SaveCommand(StorageService storageService, String address) {
        super("save",
                ": сохраняет коллекцию в файл",
                0);
        this.storageService = storageService;
        this.address =address;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        WriterFile writerFile = new WriterFile(address);
        storageService.save(writerFile);
        consoleManager.write("Коллекция сохранена в файл");

    }
}