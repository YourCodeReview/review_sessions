package com.yourcodereview.dev.yudin.console;

import java.util.Scanner;

public class Console {
    private static final String SEPARATOR = ",";
    private static final String REPLACEMENT_SYMBOL = "";
    private static final String INPUT_NULL_MESSAGE = "Input cannot be null";
    private static final String INPUT_EMPTY_MESSAGE = "Input cannot be empty";
    private static final String ERROR_MESSAGE = "Sum cannot be less or equals zero";
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. ";

    public String[] readBanknotes(String prompt, Scanner scanner) {
        boolean isGoodInput = false;
        String[] banknotes = null;
        do {
            System.out.print(prompt);

            String userInputNotes = scanner.nextLine();
            try {
                banknotes = getBanknotes(userInputNotes);
                isGoodInput = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(INCORRECT_INPUT_MESSAGE + ex.getMessage());
            }
        } while (!isGoodInput);

        return banknotes;
    }

    public int readSum(String prompt, Scanner scanner) {
        int userInputSum;
        boolean isGoodInput = false;
        do {
            System.out.print(prompt);
            userInputSum = scanner.nextInt();
            try {
                validate(userInputSum);
                isGoodInput = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(INCORRECT_INPUT_MESSAGE + ex.getMessage());
            }
        } while (!isGoodInput);

        return userInputSum;
    }

    private String[] getBanknotes(String input) {

        validateByNullOrEmpty(input);

        String inputWithoutSpaces = input.replaceAll("\\s", REPLACEMENT_SYMBOL);

        String[] banknotesStringArray = inputWithoutSpaces.split(SEPARATOR);

        return banknotesStringArray;
    }

    private void validateByNullOrEmpty(String input) {
        if (input == null)
            throw new IllegalArgumentException(INPUT_NULL_MESSAGE);
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException(INPUT_EMPTY_MESSAGE);
        }
    }

    private void validate(int input) {
        if (input <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}

