package com.yourcodereview.dev.yudin.banknote;

public class BanknoteResolver implements Resolver {
    private static final String ERROR_MESSAGE = "There is no such banknote.";

    @Override
    public Banknote resolve(int input) {

        for (Banknote value : Banknote.values()) {

            if (value.getBanknote() == input) {

                return value;
            }
        }
        throw new IllegalArgumentException("[" + input + "] " + ERROR_MESSAGE);
    }
}
