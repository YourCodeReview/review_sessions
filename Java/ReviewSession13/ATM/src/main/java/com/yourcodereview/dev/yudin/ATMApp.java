package com.yourcodereview.dev.yudin;

import com.yourcodereview.dev.yudin.dialogue.BanknoteCalculatorDialogue;
import com.yourcodereview.dev.yudin.dialogue.Dialogue;

import java.util.Scanner;

public class ATMApp {

    public static void main(String[] args) {

        Dialogue banknoteCalculatorDialogue = new BanknoteCalculatorDialogue();

        try (Scanner scanner = new Scanner(System.in)) {

            try {
                banknoteCalculatorDialogue.start(scanner);
            } catch (IllegalArgumentException exception) {
                System.err.println(exception.getMessage());
            }
        }
    }
}
