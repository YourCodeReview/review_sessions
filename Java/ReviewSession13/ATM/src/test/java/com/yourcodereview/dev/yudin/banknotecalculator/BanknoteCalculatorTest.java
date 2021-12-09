package com.yourcodereview.dev.yudin.banknotecalculator;

import com.yourcodereview.dev.yudin.banknote.BanknoteResolver;
import com.yourcodereview.dev.yudin.banknote.Resolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BanknoteCalculatorTest {

    private Resolver banknoteResolver = new BanknoteResolver();
    Calculator banknoteCalculator = new BanknoteCalculator(banknoteResolver);

    @Test
    void calculate_ShouldCountAmountBanknote_WhenInputIs1000() {

        String expected = "Banknote: 1000 - Amount: 5";

        int sum = 5000;
        String[] banknotes = {"1000"};

        StringBuilder actual = banknoteCalculator.calculate(sum, banknotes);

        assertEquals(expected, actual.toString());
    }

    @Test
    void calculate_ShouldCountAmountBanknote_WhenInputIs1000_500() {

        String expected = "Banknote: 1000 - Amount: 5\n"
                + "Banknote: 500 - Amount: 1";

        int sum = 5500;
        String[] banknotes = {"1000", "500"};

        StringBuilder actual = banknoteCalculator.calculate(sum, banknotes);

        assertEquals(expected, actual.toString());
    }

    @Test
    void calculate_ShouldThrowIllegalArgumentException_WhenInputIsNull() {

        int sum = 5000;
        String[] banknotes = null;

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteCalculator.calculate(sum, banknotes));

        String expected = "Input is null";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void calculate_ShouldThrowIllegalArgumentException_WhenInputIsNegativeValue() {

        int sum = -1;
        String[] banknotes = {"1000", "500"};

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteCalculator.calculate(sum, banknotes));

        String expected = "Sum cannot be less or equals zero";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void calculate_ShouldCountAmountBanknote_WhenInputIs1000_500_50() {

        String expected = "Banknote: 1000 - Amount: 0\n"
                + "Banknote: 500 - Amount: 0\n"
                + "Banknote: 50 - Amount: 6\n"
                + "Non-issued money: 31";

        int sum = 331;
        String[] banknotes = {"1000", "500", "50"};

        StringBuilder actual = banknoteCalculator.calculate(sum, banknotes);

        assertEquals(expected, actual.toString());
    }

    @Test
    void calculate_ShouldCountAmountBanknote_WhenInputIs20_100_1() {

        String expected = "Banknote: 100 - Amount: 2\n"
                + "Banknote: 20 - Amount: 2\n"
                + "Banknote: 1 - Amount: 18";

        int sum = 258;
        String[] banknotes = {"20", "100", "1"};

        StringBuilder actual = banknoteCalculator.calculate(sum, banknotes);

        assertEquals(expected, actual.toString());
    }

    @Test
    void calculate_ShouldThrowIllegalArgumentException_WhenInputIs20_100_1_WithWhiteSpacesAtStartAndEnd() {

        int sum = 258;
        String[] banknotes = {" 20", "100", "1 "};

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteCalculator.calculate(sum, banknotes));

        String expected = "Input can be only positive numbers";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void calculate_ShouldThrowIllegalArgumentException_WhenInputIsEmptyString() {

        int sum = 258;
        String[] banknotes = {"  "};

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteCalculator.calculate(sum, banknotes));

        String expected = "Input can be only positive numbers";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }


    @Test
    void calculate_ShouldThrowIllegalArgumentException_WhenInputIsDoesNotExistBanknotes() {

        int sum = 258;
        String[] banknotes = {"35", "50"};

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteCalculator.calculate(sum, banknotes));

        String expected = "[35] There is no such banknote.";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void calculate_ShouldThrowIllegalArgumentException_WhenInputIsLetters() {

        int sum = 258;
        String[] banknotes = {"a", "b", "c"};

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteCalculator.calculate(sum, banknotes));

        String expected = "Input can be only positive numbers";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void calculate_ShouldThrowIllegalArgumentException_WhenInputIsNumbersAndLetters() {

        int sum = 258;
        String[] banknotes = {"29847nsnsba"};

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteCalculator.calculate(sum, banknotes));

        String expected = "Input can be only positive numbers";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }
}

