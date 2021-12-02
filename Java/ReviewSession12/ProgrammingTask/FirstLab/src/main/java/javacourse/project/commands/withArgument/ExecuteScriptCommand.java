package javacourse.project.commands.withArgument;




import javacourse.project.collection.StorageService;
import javacourse.project.commands.Command;
import javacourse.project.commands.CommandManager;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class ExecuteScriptCommand extends Command {
    private StorageService storageService;
    private  static HashSet<String>  path = new HashSet<>();

    public ExecuteScriptCommand(StorageService storageService) {
        super("execute_script",
                ": считает и исполняет скрипт из указанного файла. В скрипте должны содержатся команды в таком же виде, в котором вы вводите в интерактивном режиме. ",
                1);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments {
        if (argument.length!=countOfArguments){
            throw new IncorrectNumberOfArguments("Неверное количество аргументов:" + "Пришло " + argument.length + "ожидалось " + countOfArguments);
        }
        String sciptPath = argument[0];
        Path pathToScript = Paths.get(sciptPath);
        consoleManager.write("Начинаем выполнять скрипт " + pathToScript);
        long startTime = System.currentTimeMillis();
        path.add(pathToScript.toString());
        try {
            ConsoleInterface fileInterface = new ConsoleInterface(new OutputStreamWriter(System.out), new FileReader(pathToScript.toFile()),false);
            while (fileInterface.hasNextLine()){
                String line = fileInterface.read();
                String[] listScriptPath = line.split(" ");
                if (listScriptPath[0].equals("execute_script")) {
                    if (!path.contains(listScriptPath[1])){
                        CommandManager.execute(fileInterface, line);
                    } else {
                        consoleManager.write("Вы пытаетесь вызвать скрипт, который вы уже вызывали ранее! Пытаетесь устроить рекурсию?");
                    }
                } else {
                    CommandManager.execute(fileInterface, line);
                }
            }
            path.clear();
            fileInterface.write(String.format("Скрипт %s выполнен успешно. Его выполнение заняло %d ms", pathToScript,System.currentTimeMillis() - startTime));
        } catch (FileNotFoundException e) {
            consoleManager.write("Файла не существует.");
        } catch (Exception e) {
            consoleManager.write("Ошибка во время выполнения скрипта.");
            e.printStackTrace();
        }

    }
}