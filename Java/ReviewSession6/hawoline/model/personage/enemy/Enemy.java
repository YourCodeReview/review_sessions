package ru.hawoline.alonar.model.personage.enemy;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.Slot;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.model.personage.specification.attribute.PersonageAttribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.util.Pair;

import java.util.ArrayList;

public class Enemy extends Personage {
    private String mName;
    private int mCooldown; // in seconds
    private long mTimeFromLastAttack;

    private static final long serialVersionUID = -7276415188406948963L;

    private Enemy(String name) {
        mName = name;
        mCooldown = 4;
        setAttribute(AttributeName.ENDURANCE, new PersonageAttribute(100));
        ArrayList<Slot> slots = new ArrayList<>();
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1,
                new Range(3, 4), 4, false));
        setSlots(slots);
    }

    public static Enemy createEnemy(String name) {
        return new Enemy(name);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getCooldown() {
        return mCooldown;
    }

    public void setCooldown(int cooldown) {
        mCooldown = cooldown;
    }

    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        boolean result = ((currentTime - mTimeFromLastAttack) / 1000) > mCooldown;
        if (result) {
            mTimeFromLastAttack = currentTime;
        }
        return result;
    }
}
