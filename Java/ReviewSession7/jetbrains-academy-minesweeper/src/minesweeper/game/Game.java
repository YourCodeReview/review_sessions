package minesweeper.game;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Game implements Runnable {
    private static final String INPUT_FORMAT = "[1-9] [1-9] (free|mine)";
    private static final Pattern INPUT_PATTERN = Pattern.compile(INPUT_FORMAT);
    private static final Scanner scanner = new Scanner(System.in);

    private final Board board;

    private Game(final Board board) {
        this.board = board;
    }

    public static Game create() {
        System.out.print("How many mines do you want on the field? ");
        final int minesCount = Integer.parseInt(scanner.nextLine());
        final int maxMines = Board.DEFAULT_SIZE * Board.DEFAULT_SIZE - 1;
        if (minesCount < 1 || minesCount > maxMines) {
            throw new IllegalArgumentException("The mine count should be from 1 to " + maxMines);
        }
        return new Game(new Board(minesCount));
    }

    public void run() {
        final var gameState = Stream
                .generate(this::makeSuggestion)
                .dropWhile(GameState.PLAYING::equals)
                .findFirst().orElseThrow();

        System.out.println(board);
        System.out.println(gameState);
    }

    private GameState makeSuggestion() {
        System.out.println(board);
        while (true) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            final var userInput = scanner.nextLine().toLowerCase().strip();
            if (!INPUT_PATTERN.matcher(userInput).matches()) {
                System.out.println("The correct input format is " + INPUT_FORMAT);
                continue;
            }
            final var data = userInput.split(" ");
            final int index = board.toIndex(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            if (index < 0) {
                System.out.println("The cell is already explored!");
                continue;
            }
            return board.getState(index, "mine".equals(data[2]));
        }
    }

}
