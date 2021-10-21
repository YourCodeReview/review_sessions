package ru.hawoline.alonar.model.personage.item.equipment.weapon;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.DamageSlot;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.util.Pair;

public class Weapon extends Equipment implements DamageSlot {
    private int mDistance;
    private Range mDamageRange;
    private int mRestoreTime;
    private int mCurrentRestoreTime;
    private boolean mRequiredTwoArms;

    private static final long serialVersionUID = 4335037328663998423L;

    public Weapon(String name, int requiredLevel, Quality quality, Pair<Integer, Integer> strength, Body requiredBody,
                  int distance, Range damageRange, int restoreTime, boolean requiredTwoArms) {
        super(name, requiredLevel, quality, strength, requiredBody);
        mDistance = distance;
        mDamageRange = damageRange;
        mRestoreTime = restoreTime;
        mCurrentRestoreTime = restoreTime;
        mRequiredTwoArms = requiredTwoArms;
    }

    @Override
    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }

    public Range getDamageRange() {
        return mDamageRange;
    }

    public void setDamageRange(Range damageRange) {
        mDamageRange = damageRange;
    }

    @Override
    public int getDamage() {
        return (int) (Math.random() * (mDamageRange.getHigh() - mDamageRange.getLow()) + mDamageRange.getLow());
    }

    public int getRestoreTime() {
        return mRestoreTime;
    }

    public void setRestoreTime(int restoreTime) {
        mRestoreTime = restoreTime;
    }

    public int getCurrentRestoreTime() {
        return mCurrentRestoreTime;
    }

    public void setCurrentRestoreTime(int currentRestoreTime) {
        mCurrentRestoreTime = currentRestoreTime;
    }

    public boolean isRequiredTwoArms() {
        return mRequiredTwoArms;
    }
}
