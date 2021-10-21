package ru.hawoline.alonar.model.personage.item;

import ru.hawoline.alonar.model.personage.item.state.ItemState;

import java.io.Serializable;

public abstract class Item implements Serializable {
    protected String mName;
    protected int mRequiredLevel;
    protected Quality mQuality;
    private ItemState mState;

    private static final long serialVersionUID = 8340162005259757628L;

    public Item(String name, int requiredLevel, Quality quality) {
        mName = name;
        mRequiredLevel = requiredLevel;
        mQuality = quality;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getRequiredLevel() {
        return mRequiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        mRequiredLevel = requiredLevel;
    }

    public Quality getQuality() {
        return mQuality;
    }

    public void setQuality(Quality quality) {
        mQuality = quality;
    }

    public ItemState getState() {
        return mState;
    }

    public void setState(ItemState state) {
        mState = state;
    }
}
