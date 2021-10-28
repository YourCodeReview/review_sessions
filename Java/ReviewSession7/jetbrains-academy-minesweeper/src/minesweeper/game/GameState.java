package minesweeper.game;

enum GameState {
    PLAYING(""),
    WIN("Congratulations! You found all the mines!"),
    LOSE("You stepped on a mine and failed!");

    private final String message;

    GameState(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
