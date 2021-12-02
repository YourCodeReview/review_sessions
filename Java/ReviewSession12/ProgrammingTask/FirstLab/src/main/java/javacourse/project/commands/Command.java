package javacourse.project.commands;

import javacourse.project.console.ConsoleInterface;
import javacourse.project.exceptions.IncorrectNumberOfArguments;

public abstract class Command {
    public String name;
    public String description;
    public int countOfArguments;

    public Command(String name, String description, int countOfArguments) {
        this.name = name;
        this.description = description;
        this.countOfArguments = countOfArguments;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public abstract void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArguments;
}
