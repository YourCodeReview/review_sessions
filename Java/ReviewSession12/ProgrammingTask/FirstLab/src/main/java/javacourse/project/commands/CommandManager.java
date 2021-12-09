package javacourse.project.commands;


import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;
import javacourse.project.exceptions.NoSuchCommandException;

import java.util.*;

public  class CommandManager {

    private static Map<String, Command> allCommand = new HashMap<>();
    private static LinkedList<String> commandList = new LinkedList<>();


    public CommandManager(Command ... commands) {
        addCommand(commands);
    }

    public static void execute(ConsoleInterface consoleManager, String line) throws IncorrectNumberOfArguments {
        String[] args = line.trim().split(" ");
        commandList.add(args[0]);
        Command command = getCommand(args[0]);
        String[] parameters = Arrays.copyOfRange(args, 1, args.length);
        command.execute(consoleManager, parameters);
    }

    public static List<Command> getAllCommand() {
        return allCommand.values().stream().toList();
    }

    private static Command getCommand(String nameCommand) throws NoSuchCommandException {
        if (allCommand.containsKey(nameCommand)) {
            return allCommand.get(nameCommand);
        } else {
            throw new NoSuchCommandException("Такой команды не существует. Попробуйте заново ввести или воспользуйтесь командой help");
        }
    }

    private  void addCommand(Command [] commands){
        Arrays.stream(commands).forEach(x->{
            allCommand.put(x.getName(), x);
        });
    }
    public static List<String> getCommandList() {
        if (commandList.size() < 15) {
            return commandList;
        } else {
            return commandList.stream().limit(15).toList();
        }
    }
}

    /*
TODO
    также я бы хотел услышать какие то комментарии насчет этого класса. Меня как учили, что мы должны избегать каких утильных классов.
    Делать максимально по ООПшному. Ну пока я что я в этом не сильно просто разбираюсь. Но основной функционал это класса я достаю с помощью его
    статических методов. Это нормально?

 */

