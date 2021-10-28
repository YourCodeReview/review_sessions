package minesweeper.game;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public final class Board {
    public static final int DEFAULT_SIZE = 9;

    private final int size = DEFAULT_SIZE;
    private final CellState[] field;
    private final Set<Integer> mines;
    private final String template;
    private final int minesCount;

    Board(final int minesCount) {
        this.minesCount = minesCount;
        this.mines = new HashSet<>(minesCount);
        this.template = createTemplate();
        this.field = new CellState[size * size];
        Arrays.fill(field, CellState.UNKNOWN);
    }

    private void placeMines(final int firstSuggestion) {
        final var indexes = range(0, field.length).boxed()
                .collect(toCollection(ArrayList::new));
        indexes.remove(firstSuggestion);
        Collections.shuffle(indexes);
        mines.addAll(indexes.subList(0, minesCount));
    }

    GameState getState(final int index, final boolean isMineMark) {
        if (isMineMark) {
            flipMark(index);
            return isAllMinesMarked() ? GameState.WIN : GameState.PLAYING;
        }
        if (mines.isEmpty()) {
            placeMines(index);
        }
        if (mines.contains(index)) {
            showMines();
            return GameState.LOSE;
        }
        exploreCell(index);
        return isAllExplored() ? GameState.WIN : GameState.PLAYING;
    }

    int toIndex(final int x, final int y) {
        final int index = (y - 1) * size + (x - 1);
        return isUnexplored(index) ? index : -1;
    }

    private boolean isAllExplored() {
        return range(0, field.length).filter(this::isUnexplored).count() == mines.size();
    }

    private boolean isUnexplored(final int index) {
        return CellState.UNEXPLORED.contains(field[index]);
    }

    private boolean isAllMinesMarked() {
        return mines.size() > 0 && range(0, field.length)
                .filter(i -> field[i] == CellState.MARK)
                .boxed()
                .collect(toUnmodifiableSet())
                .equals(mines);
    }

    private void flipMark(final int index) {
        field[index] = field[index] == CellState.MARK ? CellState.UNKNOWN : CellState.MARK;
    }

    private void showMines() {
        mines.forEach(index -> field[index] = CellState.MINE);
    }

    private void exploreCell(final int index) {
        final int number = countMines(index);
        field[index] = CellState.values()[number];

        if (number == 0) {
            neighbors(index).filter(this::isUnexplored).forEach(this::exploreCell);
        }
    }

    private int countMines(final int index) {
        return (int) neighbors(index).filter(mines::contains).count();
    }

    private IntStream neighbors(final int index) {
        return IntStream
                .of(-size - 1, -size, -size + 1, -1, 1, size - 1, size, size + 1)
                .filter(offset -> inRange(index, offset))
                .map(offset -> index + offset);
    }

    private boolean inRange(final int index, final int offset) {
        return inRange(index % size + offset - offset / (size - 1) * size)
                && inRange(index / size + offset / (size - 1));
    }

    private boolean inRange(final int x) {
        return x >= 0 && x < size;
    }

    @Override
    public String toString() {
        return String.format(template, (Object[]) field);
    }

    private String createTemplate() {
        return ""
                + " │123456789│%n"
                + "—│—————————│%n"
                + rangeClosed(1, size).mapToObj(row -> row + "│%s%s%s%s%s%s%s%s%s│%n").collect(joining())
                + "—│—————————│";
    }
}