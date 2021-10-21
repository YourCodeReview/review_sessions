package ru.hawoline.alonar.presenter;

import android.content.Context;
import android.os.Bundle;
import ru.hawoline.alonar.model.map.LandscapeMap;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.*;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.model.personage.heroclass.HeroClass;
import ru.hawoline.alonar.model.personage.usecase.DamageComputationUseCase;
import ru.hawoline.alonar.model.personage.usecase.EnemyAttackComputationUseCase;
import ru.hawoline.alonar.util.Pair;
import ru.hawoline.alonar.view.MainView;
import ru.hawoline.alonar.view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MainPresenterImpl implements MainPresenter {
    private MainView mMainView;
    private Map mGameMap;
    private ArrayList<Enemy> mEnemiesAroundHero;
    private Personage mPersonage;
    private Location mPersonageLocation;
    private HashMap<Personage, Location> mPersonages;
    private ConcurrentHashMap<Enemy, Location> mEnemies;
    private EnemyAttackComputationUseCase mEnemyAttackComputationUseCase;

    public MainPresenterImpl() {
        mGameMap = new LandscapeMap(30);
        mEnemiesAroundHero = new ArrayList<>();

        mPersonages = new HashMap<>();
        mPersonage = PersonageFactory.createPersonage(HeroClass.MAGE);
        mPersonageLocation = new Location(1, 1);
        mPersonages.put(mPersonage, mPersonageLocation);

        mEnemies = new ConcurrentHashMap<>();
        for (int enemyIndex = 0; enemyIndex < 20; enemyIndex++) {
            Enemy enemy = Enemy.createEnemy("Rat");
            mEnemies.put(enemy, new Location(
                    (int) Math.floor(Math.random() * (mGameMap.getSize() - 2) + 1),
                    (int) Math.floor(Math.random() * mGameMap.getSize()))
            );
        }

        mEnemyAttackComputationUseCase =
                new EnemyAttackComputationUseCase(mEnemies, new Pair<>(mPersonage, mPersonageLocation));
    }

    @Override
    public void attachView(View view) {
        this.mMainView = (MainView) view;
    }

    @Override
    public void detachView() {
        this.mMainView = null;
    }

    @Override
    public void saveInstance(Bundle state) {
        try {
            FileOutputStream heroFileOutputStream = mMainView.getContext().openFileOutput("Hero.out", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(heroFileOutputStream);
            objectOutputStream.writeObject(mPersonage);
            objectOutputStream.close();
            FileOutputStream heroLocationFileOutputStream = mMainView.getContext().openFileOutput("HeroLocation.out", Context.MODE_PRIVATE);
            ObjectOutputStream heroLocationOutputStream = new ObjectOutputStream(heroLocationFileOutputStream);
            heroLocationOutputStream.writeObject(mPersonageLocation);
            heroLocationOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreInstance(Bundle state) {
        try {
            FileInputStream heroFileInputStream = mMainView.getContext().openFileInput("Hero.out");
            ObjectInputStream objectInputStream = new ObjectInputStream(heroFileInputStream);
            mPersonage = (Personage) objectInputStream.readObject();
            objectInputStream.close();
            FileInputStream heroLocationFileInputStream = mMainView.getContext().openFileInput("HeroLocation.out");
            ObjectInputStream heroLocationInputStream = new ObjectInputStream(heroLocationFileInputStream);
            mPersonageLocation = (Location) heroLocationInputStream.readObject();
            heroLocationInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        mEnemyAttackComputationUseCase.setEnemies(mEnemies);
        mEnemyAttackComputationUseCase.setHero(new Pair<>(mPersonage, mPersonageLocation));
    }

    @Override
    public int[][] getGameMap() {
        return mGameMap.getMap();
    }

    @Override
    public Personage getPersonage() {
        return mPersonage;
    }

    @Override
    public Location getPersonageLocation() {
        if (mPersonage.getHealth() < 1) {
            mPersonage.setHealth(100);
            mPersonageLocation.setX(1);
            mPersonageLocation.setY(1);
        }
        return mPersonageLocation;
    }

    @Override
    public void onPersonageMove(int x, int y) {
        Location personageLocation = getPersonageLocation();
        int newXCoordinate = personageLocation.getX() + x;
        int newYCoordinate = personageLocation.getY() + y;
        if (mGameMap.getSize() > newXCoordinate && mGameMap.getSize() > newYCoordinate || newXCoordinate > -1 || newYCoordinate > -1) {
            personageLocation.move(x, y);
        }
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero() {
        mEnemiesAroundHero.clear();
        ConcurrentHashMap<Enemy, Location> enemies = mEnemies;
        Location personageLocation = getPersonageLocation();
        for (Enemy enemy: enemies.keySet()) {
            if (Math.abs(enemies.get(enemy).getX() - personageLocation.getX()) < 3
                    && Math.abs(enemies.get(enemy).getY() - personageLocation.getY()) < 3) {
                mEnemiesAroundHero.add(enemy);
            }
        }
        return mEnemiesAroundHero;
    }

    @Override
    public ArrayList<Enemy> findEnemiesAroundHero(int slotIndex) {
        mEnemiesAroundHero.clear();
        DamageSlot slot = (DamageSlot) mPersonage.getSlots().get(slotIndex);
        Location personageLocation = getPersonageLocation();
        int distance = slot.getDistance();
        for (Enemy enemy: mEnemies.keySet()) {
            Location enemyLocation = mEnemies.get(enemy);
            int xDistance = personageLocation.getX() - enemyLocation.getX();
            int yDistance = personageLocation.getY() - enemyLocation.getY();
            int sum = Math.abs(xDistance) + Math.abs(yDistance);
            int diagonalSquaredDistance = xDistance * xDistance + yDistance * yDistance;
            if (distance == 0) {
                if (xDistance == 0 && yDistance == 0) {
                    mEnemiesAroundHero.add(enemy);
                }
            } else if (distance == 3) {
                if (sum < 2) {
                    mEnemiesAroundHero.add(enemy);
                }
            } else if (distance == 4) {
                if (diagonalSquaredDistance == 2) {
                    mEnemiesAroundHero.add(enemy);
                }
            } else if (distance == 6) {
                if (sum < 3) {
                    mEnemiesAroundHero.add(enemy);
                }
            }
        }

        return mEnemiesAroundHero;
    }

    @Override
    public void enemyAttacked(Enemy enemy, int slotIndex) {
        DamageComputationUseCase.compute(getPersonage(), enemy, slotIndex);
        if (enemy.getHealth() < 1) {
            mEnemies.remove(enemy);
            mMainView.removeEnemyTextView();
        }
    }

    @Override
    public Location getEnemyLocation(Enemy enemy) {
        return mEnemies.get(enemy);
    }
}
