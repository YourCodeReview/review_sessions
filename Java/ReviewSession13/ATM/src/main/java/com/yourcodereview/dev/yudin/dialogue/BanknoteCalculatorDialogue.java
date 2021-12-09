package com.yourcodereview.dev.yudin.dialogue;

import com.yourcodereview.dev.yudin.banknote.BanknoteResolver;
import com.yourcodereview.dev.yudin.banknote.Resolver;
import com.yourcodereview.dev.yudin.banknotecalculator.BanknoteCalculator;
import com.yourcodereview.dev.yudin.banknotecalculator.Calculator;
import com.yourcodereview.dev.yudin.console.Console;

import java.util.Scanner;

public class BanknoteCalculatorDialogue implements Dialogue {
    private static final String GREETING_MESSAGE = "ATM machine greetings to you!";
    private static final String SUM_MESSAGE = "Enter sum: ";
    private static final String NOTES_MESSAGE = "Enter banknotes: ";
    private static final String REPEAT_MESSAGE = "Do you wanna to try again? [yes/no]";
    private static final String USER_ANSWER = "Answer: ";
    private static final String CONTINUE_ANSWER = "yes";

    private Resolver banknoteResolver = new BanknoteResolver();
    private Calculator banknoteCalculator = new BanknoteCalculator(banknoteResolver);
    private Console console = new Console();

    @Override
    public void start(Scanner scanner) {
        String userAnswer;

        do {
            System.out.println(GREETING_MESSAGE);

            int sum = console.readSum(SUM_MESSAGE, scanner);

            scanner.nextLine();

            String[] banknotes = console.readBanknotes(NOTES_MESSAGE, scanner);

            System.out.println(banknoteCalculator.calculate(sum, banknotes));

            userAnswer = tryAgain(scanner);
        } while (CONTINUE_ANSWER.equals(userAnswer));
    }

    private String tryAgain(Scanner scanner) {
        System.out.println(REPEAT_MESSAGE);
        System.out.print(USER_ANSWER);

        String answer = scanner.nextLine().toLowerCase();
        System.out.println();

        return answer;
    }
}
