package com.yourcodereview.dev.yudin.banknote;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BanknoteResolverTest {
    BanknoteResolver banknoteResolver = new BanknoteResolver();

    @Test
    void resolve_ShouldBanknote_WhenInputIs_1000() {

        int input = 1000;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 1000;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_500() {

        int input = 500;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 500;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_200() {

        int input = 200;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 200;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_100() {

        int input = 100;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 100;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_50() {

        int input = 50;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 50;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_20() {

        int input = 20;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 20;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_10() {

        int input = 10;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 10;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_5() {

        int input = 5;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 5;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_2() {

        int input = 2;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 2;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldBanknote_WhenInputIs_1() {

        int input = 1;
        Banknote banknote = banknoteResolver.resolve(input);

        int actual = banknote.getBanknote();
        int expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldThrowIllegalArgumentException_WhenInputIsDoesNotExistBanknote35() {

        int input = 35;

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteResolver.resolve(input).getBanknote());

        String expected = "[35] There is no such banknote.";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    void resolve_ShouldThrowIllegalArgumentException_WhenInputIsNegativeValue() {

        int input = -1;

        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> banknoteResolver.resolve(input).getBanknote());

        String expected = "[-1] There is no such banknote.";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }
}
