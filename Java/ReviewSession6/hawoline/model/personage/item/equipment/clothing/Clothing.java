package ru.hawoline.alonar.model.personage.item.equipment.clothing;

import ru.hawoline.alonar.model.personage.item.equipment.Body;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.util.Pair;

public class Clothing extends Equipment {
    private int mArmor;
    private ClothingType mClothingType;

    private static final long serialVersionUID = -6994483391440650649L;

    public Clothing(String name, int requiredLevel, Quality quality, Pair<Integer, Integer> strength, Body requiredBody) {
        super(name, requiredLevel, quality, strength, requiredBody);
        setClothingType(ClothingType.RAG);
    }

    public int getArmor() {
        return mArmor;
    }

    public void setArmor(int armor) {
        mArmor = armor;
    }

    public ClothingType getClothingType() {
        return mClothingType;
    }

    public void setClothingType(ClothingType clothingType) {
        mClothingType = clothingType;
    }
}
