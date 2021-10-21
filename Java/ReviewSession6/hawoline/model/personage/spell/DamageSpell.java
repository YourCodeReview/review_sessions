package ru.hawoline.alonar.model.personage.spell;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.DamageSlot;

public class DamageSpell extends Spell implements DamageSlot {
    private int mDamage;
    private Range mDamageRange;

    public DamageSpell(String name, int distance, int requiredMana, int restoreTime, Range damageRange) {
        super(name, distance, requiredMana, restoreTime);
        this.mDamageRange = damageRange;
        calculateDamage();
    }

    public Range getDamageRange() {
        return mDamageRange;
    }

    public void setDamageRange(Range damageRange) {
        mDamageRange = damageRange;
    }

    public int calculateDamage() {
        mDamage = (int) (Math.random() * (mDamageRange.getDistance()) + mDamageRange.getLow());
        return mDamage;
    }

    @Override
    public int getDamage() {
        return calculateDamage();
    }
}
