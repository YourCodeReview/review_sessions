package ru.hawoline.alonar.model.personage.usecase;

import ru.hawoline.alonar.model.gamelog.GameLog;
import ru.hawoline.alonar.model.personage.DamageSlot;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.Slot;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.model.personage.spell.DamageSpell;
import ru.hawoline.alonar.model.personage.item.equipment.weapon.Weapon;

public final class DamageComputationUseCase {
    public static void compute(Personage attacker, Personage defender, int damageSlotIndex) {
        Slot slot = attacker.getSlots().get(damageSlotIndex);

        if (slot instanceof DamageSpell) {
            if (attacker.getMp() >= ((DamageSpell) slot).getRequiredMana()) {
                defender.setHealth(defender.getHealth() - ((DamageSlot) slot).getDamage());
                attacker.setMp(attacker.getMp() - ((DamageSpell) slot).getRequiredMana());
                if (attacker instanceof Enemy) {
                    GameLog.getInstance().putToLog(((Enemy) attacker).getName() + " attacks the hero " +
                            ((DamageSpell) slot).getDamage() + " magic damage");
                } else {
                    GameLog.getInstance().putToLog("Hero attacks the enemy " + ((DamageSpell) slot).getDamage() +
                            " magic damage");
                }
            }
        } else if (slot instanceof Weapon) {
            int damage = (int) (((DamageSlot) slot).getDamage() * (100 - defender.getArmor()) * 0.01);
            defender.setHealth(defender.getHealth() - damage);

            if (attacker instanceof Enemy) {
                GameLog.getInstance().putToLog(((Enemy) attacker).getName() + " attacks the hero " + damage +
                        " weapon damage");
            } else {
                GameLog.getInstance().putToLog("Hero attacks the enemy " + damage + " weapon damage");
            }
        }
    }
}
