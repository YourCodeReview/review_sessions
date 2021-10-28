import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;

public class GameStep {
    private static final String EXPECTED_SYMBOLS = "[./*x1-9]*";
    static final int SIZE = 9;
    static final int CELLS_COUNT = SIZE * SIZE;

    private final String message;
    private final String board;

    private GameStep(String board, String message) {
        this.message = message;
        this.board = board;
    }

    static GameStep parse(final String output) {
        final var data = output.toLowerCase().strip()
                .replace("|", "│")
                .replace("-", "—");

        final var lines = data.lines().toArray(String[]::new);
        Assert.that(lines.length > 0, "no_output_found", lines.length);
        final var message = lines[lines.length - 1];
        if (lines.length < 3) {
            // Possible error message like: There is a number here!
            return new GameStep("", message);
        }
        Assert.that(lines.length >= 13, "less_then_13_lines", lines.length);

        Assert.find(message, "failed|congratulations|unset mines", "no_last_message");

        Assert.contains(data, "│123456789│", "board_header_numbers");

        final var board = data.replaceAll("(?s).*?│.{1,3}1│|│.{1,3}\\d│|│.*", "");
        final var cellsCount = SIZE * SIZE;

        Assert.that(board.length() == cellsCount, "cells_number_incorrect", cellsCount, board.length());
        Assert.matches(board, EXPECTED_SYMBOLS, "illegal_symbol", board.replaceAll(EXPECTED_SYMBOLS, ""));

        final BiPredicate<Character, Character> noNeighbors = (first, second) -> range(0, cellsCount)
                .filter(index -> board.charAt(index) == first)
                .noneMatch(index -> neighbors(index).map(board::charAt).anyMatch(second::equals));

        Assert.that(noNeighbors.test('.', '/'), "impossible_slash_dot");
        Assert.that(noNeighbors.test('x', '/'), "impossible_slash_x");
        Assert.that(noNeighbors.test('*', '/'), "impossible_slash_asterisk");
        Assert.that(board.indexOf('x') == -1 || message.contains("failed"), "no_failed_and_x");

        final var gameStep = new GameStep(board, message);
        gameStep.checkNumbers();

        return gameStep;
    }

    private void checkNumbers() {
        final IntPredicate checkCells =  isFailed() ? i -> board.charAt(i) == 'x' : this::isUnexplored;

        final var wrongNumber = range(0, CELLS_COUNT)
                .filter(this::isNumber)
                .filter(index-> this.getNumber(index) > neighbors(index).filter(checkCells).count())
                .findAny();

        if (wrongNumber.isPresent()) {
            final var index = wrongNumber.getAsInt();
            final var number = getNumber(index);
            final var mines = neighbors(index).filter(checkCells).count();
            throw Assert.error(isFailed() ? "wrong_number_failed" : "wrong_number_playing", number, mines);
        }
    }

    boolean isError() {
        return board.isEmpty();
    }

    boolean isFailed() {
        return message.contains("failed");
    }

    boolean isWin() {
        return message.contains("congratulations");
    }

    boolean isPlaying() {
        return message.contains("unset mines");
    }

    boolean isSureMine(final int index) {
        return neighbors(index).filter(this::isAllUnexploredMines).findAny().isPresent();
    }

    boolean isSureFree(final int index) {
        return neighbors(index).filter(this::isAllMineAroundMarked).findAny().isPresent();
    }

    private boolean isAllMineAroundMarked(final int index) {
        return getNumber(index) == neighbors(index).filter(this::isMarked).count();
    }

    private boolean isAllUnexploredMines(final int index) {
        return getNumber(index) == neighbors(index).filter(this::isUnexplored).count();
    }

    boolean isDot(final int index) {
        return board.charAt(index) == '.';
    }

    boolean isMarked(final int index) {
        return board.charAt(index) == '*';
    }

    boolean isUnexplored(final int index) {
        return board.charAt(index) == '.' || board.charAt(index) == '*';
    }

    boolean isNumber(final int index) {
        return '0' < board.charAt(index) && board.charAt(index) <= '9';
    }

    private int getNumber(final int index) {
        return isNumber(index) ? Character.digit(board.charAt(index), 10) : -1;
    }

    int count(final char symbol) {
        return (int) board.chars().filter(c -> c == symbol).count();
    }

    int getRandomFreeIndex() {
        final var freeCells = freeIndexes()
                .boxed().collect(toCollection(LinkedList::new));
        Assert.that(freeCells.size() > 0, "no_dots");
        Collections.shuffle(freeCells);
        return freeCells.getFirst();
    }

    IntStream freeIndexes() {
        return range(0, CELLS_COUNT).filter(this::isDot);
    }

    private static IntStream neighbors(final int index) {
        return IntStream
                .of(-SIZE - 1, -SIZE, -SIZE + 1, -1, 1, SIZE - 1, SIZE, SIZE + 1)
                .filter(offset -> inRange(index, offset))
                .map(offset -> index + offset);
    }

    private static boolean inRange(final int index, final int offset) {
        return inRange(index % SIZE + offset - offset / (SIZE - 1) * SIZE)
                && inRange(index / SIZE + offset / (SIZE - 1));
    }

    private static boolean inRange(final int x) {
        return x >= 0 && x < SIZE;
    }

}
