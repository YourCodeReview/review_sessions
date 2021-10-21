package ru.hawoline.alonar.model.personage;

import ru.hawoline.alonar.model.Range;
import ru.hawoline.alonar.model.personage.heroclass.*;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.model.personage.specification.attribute.PersonageAttribute;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.personage.spell.DamageSpell;
import ru.hawoline.alonar.model.personage.item.equipment.weapon.Knife;
import ru.hawoline.alonar.util.Pair;

import java.util.ArrayList;

public class PersonageFactory {

    public static Personage createPersonage(HeroClass heroClass) {
        Personage personage;
        if (heroClass == HeroClass.MAGE) {
            personage = new Mage();
        } else if (heroClass == HeroClass.PALADIN) {
            personage = new Paladin();
        } else if (heroClass == HeroClass.PRIEST) {
            personage = new Priest();
        } else if (heroClass == HeroClass.RANGER) {
            personage = new Ranger();
        } else if (heroClass == HeroClass.ROBBER) {
            personage = new Robber();
        } else {
            personage = new Warrior();
        }
        personage.setAttribute(AttributeName.ENDURANCE, new PersonageAttribute(5));
        personage.setAttribute(AttributeName.STRENGTH, new PersonageAttribute(5));
        personage.setAttribute(AttributeName.INTELLIGENCE, new PersonageAttribute(100));
        personage.setAttribute(AttributeName.AGILITY, new PersonageAttribute(5));
        personage.setAttribute(AttributeName.SPIRIT, new PersonageAttribute(5));

        Knife knife = new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4,false);
        personage.equip(Body.ARMS, knife);
        personage.setArmor(10);
        ArrayList<Slot> slots = new ArrayList<>();
        slots.add(knife);
        slots.add(new DamageSpell("Fireball", 6, 50, 6, new Range(197, 236)));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4,false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4,false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4, false));
        slots.add(new Knife("Knife", 1, Quality.NORMAL, new Pair<>(12, 12), Body.ARMS, 1, new Range(3, 4), 4, false));
        personage.setSlots(slots);
        return personage;
    }
}
