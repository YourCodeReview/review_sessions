package minesweeper.game;

import java.util.Set;

enum CellState {
    ZERO('/'),
    ONO('1'),
    TWO('2'),
    TREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    MINE('X'),
    MARK('*'),
    UNKNOWN('.');

    static final Set<CellState> UNEXPLORED = Set.of(UNKNOWN, MARK);

    private final char symbol;

    CellState(final char symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
