package ru.hawoline.alonar.model.personage;

public interface DamageSlot extends Slot {
    int getDamage();
    /**
     * 0 - close distance
     * 3 - one cell distance
     * 4 - one diagonal cell distance
     * 6 - two cell distance
     */
    int getDistance();
}
