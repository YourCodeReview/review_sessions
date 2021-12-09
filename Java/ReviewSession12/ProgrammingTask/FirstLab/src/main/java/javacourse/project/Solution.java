package javacourse.project;

import javacourse.project.collection.PersonStorage;
import javacourse.project.collection.Storage;
import javacourse.project.collection.StorageService;
import javacourse.project.collection.StorageServiceImpl;
import javacourse.project.commands.CommandManager;
import javacourse.project.commands.withArgument.*;
import javacourse.project.commands.withoutArgument.*;
import javacourse.project.console.AskerArgument;
import javacourse.project.console.ConsoleInterface;
import javacourse.project.data.Person;
import javacourse.project.exceptions.IncorrectNumberOfArguments;
import javacourse.project.exceptions.NoSuchCommandException;
import javacourse.project.fileWorkers.JsonReader;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Solution {

    public static void main(String[] args) {
        /*TODO разобрать два случая:
         *  1) когда файл передается с помощью аргумента командной строки
         *  2) получать адрес файла с помощью переменной окружения  */
        String address = "src/main/resources/testing.json";
        /*
          String address = System.getenv("FILE_WORK");
          String address = args[0]; */
        Storage<Long, Person> personStorage = new PersonStorage();

        StorageService storageService = new StorageServiceImpl(personStorage);
        AskerArgument askerArgument = new AskerArgument();
        CommandManager commandManager = new CommandManager(new ClearCommand(storageService),
                new CountLessThanLocation(storageService,askerArgument),
                new ExecuteScriptCommand(storageService),
                new ExitCommand(storageService),
                new HelpCommand(storageService),
                new HistoryCommand(storageService),
                new InfoCommand(storageService),
                new InsertCommand(storageService,askerArgument),
                new PrintAscendingCommand(storageService),
                new RemoveAnyByBirthday(storageService),
                new RemoveCommand(storageService),
                new RemoveGreaterCommand(storageService, askerArgument),
                new RemoveGreaterKeyCommand(storageService),
                new SaveCommand(storageService,address),
                new ShowCommand(storageService),
                new UpdateCommand(storageService,askerArgument));
        ConsoleInterface consoleManager = new ConsoleInterface(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), new InputStreamReader(System.in, StandardCharsets.UTF_8), true);
        JsonReader jsonReader = new JsonReader();
        jsonReader.read(address, personStorage.getCollection());
        storageService.updateSetId();


        while (true){
            if (consoleManager.hasNextLine()){
                String line = consoleManager.read();
                try {
                    commandManager.execute(consoleManager, line);
                } catch (IncorrectNumberOfArguments e) {
                    e.printStackTrace();
                }catch (NoSuchCommandException e){
                    e.printStackTrace();
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    consoleManager.write("Вы ввели некорректное число, попробуйте запустить команду заново");
                } catch (Exception e){
                    consoleManager.write("Произошла ошибка, автор не заметил её при тестировании! Извините, я все исправлю. ");
                    e.printStackTrace();
                }
            }
        }




    }
}


