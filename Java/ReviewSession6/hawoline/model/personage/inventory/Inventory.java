package ru.hawoline.alonar.model.personage.inventory;

import ru.hawoline.alonar.model.personage.item.Item;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Bag> mBags;

    public Inventory() {
        mBags = new ArrayList<>();
        mBags.add(new Bag(new ArrayList<>(), 4));
        mBags.add(new Bag(new ArrayList<>(), 4));
        mBags.add(new Bag(new ArrayList<>(), 4));
    }

    public ArrayList<Bag> getBags() {
        return mBags;
    }

    public void setBags(ArrayList<Bag> bags) {
        mBags = bags;
    }

    public void addItem(Item item) {
        for (Bag bag: getBags()) {
            bag.addItem(item);
        }
    }
    public boolean removeItem(Item item) {
        for (Bag bag: getBags()) {
            if (bag.removeItem(item)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasFreeSpace() {
        for (Bag bag: getBags()) {
            if (bag.getCapacity() - bag.getItemCount() > 0) {
                return true;
            }
        }

        return false;
    }
}
