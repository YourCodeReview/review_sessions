package ru.hawoline.alonar.presenter;

import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;

import java.util.ArrayList;

public interface MainPresenter extends Presenter {
    int[][] getGameMap();

    Personage getPersonage();

    Location getPersonageLocation();

    void onPersonageMove(int x, int y);

    ArrayList<Enemy> findEnemiesAroundHero();

    ArrayList<Enemy> findEnemiesAroundHero(int slotIndex);

    void enemyAttacked(Enemy enemy, int slotIndex);

    Location getEnemyLocation(Enemy enemy);
}
