package ru.hawoline.alonar.model.gamelog;

public class GameLog extends Log {
    private static Log mGameLog;

    private GameLog() {
        super(10);
    }

    public static Log getInstance() {
        if (mGameLog == null) {
            mGameLog = new GameLog();
        }

        return mGameLog;
    }
}
