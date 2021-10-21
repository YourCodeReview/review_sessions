package ru.hawoline.alonar.model.personage;

import androidx.annotation.NonNull;
import ru.hawoline.alonar.model.personage.effect.Effect;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.model.personage.specification.Vitality;
import ru.hawoline.alonar.model.personage.specification.VitalityType;
import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;
import ru.hawoline.alonar.model.personage.specification.attribute.PersonageAttribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Personage implements Serializable {
    private ArrayList<Vitality> mVitality;
    private HashMap<AttributeName, PersonageAttribute> mAttributes;
    private HashMap<Body, Equipment> mEquipment;
    private Inventory mInventory;
    private ArrayList<Slot> mSlots;
    private int mExperience;
    private int mArmor; // In percent. Max is 80%

    private static final long serialVersionUID = -1613269264133657958L;

    protected Personage() {
        mAttributes = new HashMap<>();
        mVitality = new ArrayList<>();
        mVitality.add(new Vitality(VitalityType.HEATH, 10));
        mVitality.add(new Vitality(VitalityType.MP, 10));
        mEquipment = new HashMap<>();
        mExperience = 0;
        mArmor = 10;
        mInventory = new Inventory();
    }

    private void addEquipmentEffectBonuses() {
        for (Body body: mEquipment.keySet()) {
            for (Effect effect: mEquipment.get(body).getEffects()) {
                mAttributes.get(effect.getAttributeName()).increase(effect.getValue());
            }
        }
    }

    public int getExperience() {
        return mExperience;
    }

    public void setExperience(int experience) {
        mExperience = experience;
    }

    public int getArmor() {
        return mArmor;
    }

    public void setArmor(int armor) {
        mArmor = armor;
    }

    public int getHealth() {
        return mVitality.get(Vitality.HP).getResidualMax().getSecond();
    }
    public void setHealth(int heath) {
        mVitality.get(Vitality.HP).setValue(heath);
    }

    public int getMp() {
        return mVitality.get(Vitality.MP).getResidualMax().getSecond();
    }
    public void setMp(int mp) {
        mVitality.get(Vitality.MP).setValue(mp);
    }

    public void setAttribute(AttributeName attributeName, PersonageAttribute personageAttributeValue) {
        mAttributes.put(attributeName, personageAttributeValue);
        if (attributeName == AttributeName.ENDURANCE) {
            mVitality.get(Vitality.HP).setMaxValue(personageAttributeValue.getMax() * 10);
        } else if (attributeName == AttributeName.INTELLIGENCE) {
            mVitality.get(Vitality.MP).setMaxValue(personageAttributeValue.getMax() * 16);
        }
    }

    public PersonageAttribute getAttribute(AttributeName attributeName) {
        return mAttributes.get(attributeName);
    }

    public HashMap<Body, Equipment> getEquipment() {
        return mEquipment;
    }

    public void setEquipment(HashMap<Body, Equipment> equipment) {
        mEquipment = equipment;
    }

    public Inventory getInventory() {
        return mInventory;
    }

    public void setInventory(Inventory inventory) {
        mInventory = inventory;
    }

    public void equip(Body body, @NonNull Equipment equipment) {
        unequip(body);
        if (equipment.getRequiredBody() == body) {
            mEquipment.put(body, equipment);
            for (Effect effect: equipment.getEffects()) {
                mAttributes.get(effect.getAttributeName()).increase(effect.getValue());
            }
        }
    }

    public void unequip(Body body) {
        if (mEquipment.get(body) == null) {
            return;
        }
        for (Effect effect: mEquipment.get(body).getEffects()) {
            mAttributes.get(effect.getAttributeName()).decrease(effect.getValue());
        }
        mEquipment.remove(body);
    }

    public ArrayList<Slot> getSlots() {
        return mSlots;
    }

    public void setSlots(ArrayList<Slot> slots) {
        mSlots = slots;
    }
}
