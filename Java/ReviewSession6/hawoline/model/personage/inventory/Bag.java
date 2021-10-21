package ru.hawoline.alonar.model.personage.inventory;

import ru.hawoline.alonar.model.personage.item.Item;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Item> mItems;
    private int mCapacity;
    private int mItemCount;

    public Bag(ArrayList<Item> items, int capacity) {
        mCapacity = capacity;
        mItems = items;
        mItemCount = items.size();
    }

    public void addItem(Item item) {
        if (mItems.add(item)) {
            mItemCount++;
        }
    }

    public boolean removeItem(Item item) {
        boolean result = mItems.remove(item);
        if (result) {
            mItemCount--;
        }

        return result;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public int getItemCount() {
        return mItemCount;
    }
}
