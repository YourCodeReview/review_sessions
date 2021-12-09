package com.yourcodereview.dev.yudin.banknotecalculator;

import com.yourcodereview.dev.yudin.banknote.Resolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class BanknoteCalculator implements Calculator {
    private static final String INPUT_NULL_MESSAGE = "Input is null";
    private static final String ERROR_MESSAGE = "Sum cannot be less or equals zero";
    private static final String DIGITS_REGEX = "[0-9]+";
    private static final String INPUT_POSITIVE_NUMBER_MESSAGE = "Input can be only positive numbers";

    private Resolver banknoteResolver;

    public BanknoteCalculator(Resolver banknoteResolver) {
        this.banknoteResolver = banknoteResolver;
    }

    @Override
    public StringBuilder calculate(int sum, String[] banknotesData) {

        validate(sum, banknotesData);

        Integer[] sortedBanknotes = getSortedBanknotes(banknotesData);

        StringBuilder result = new StringBuilder();

        for (int banknote : sortedBanknotes) {
            int count = 0;

            while (sum >= banknote) {
                sum = sum - banknote;
                count++;
            }
            result.append("Banknote: ").append(banknote)
                    .append(" - ")
                    .append("Amount: ").append(count)
                    .append("\n");
        }
        if (sum > 0) {
            result.append("Non-issued money: ").append(sum).append("\n");
        }
        result.deleteCharAt(result.length() - 1);

        return result;
    }

    private Integer[] getSortedBanknotes(String[] input) {

        int amountBanknotes = input.length;

        Integer[] banknotes = new Integer[amountBanknotes];

        for (int i = 0; i < amountBanknotes; i++) {
            String banknote = input[i];

            validateByDigits(banknote);

            banknotes[i] = Integer.parseInt(banknote);

            banknoteResolver.resolve(banknotes[i]);
        }
        Arrays.sort(banknotes, Collections.reverseOrder());

        return banknotes;
    }

    private void validateByDigits(String input) {
        if (!input.matches(DIGITS_REGEX)) {
            throw new IllegalArgumentException(INPUT_POSITIVE_NUMBER_MESSAGE);
        }
    }

    private void validate(int input, String[] banknotesInput) {
        if (input <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        if (banknotesInput == null) {
            throw new IllegalArgumentException(INPUT_NULL_MESSAGE);
        }
    }
}
