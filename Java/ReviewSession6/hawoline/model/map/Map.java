package ru.hawoline.alonar.model.map;

public abstract class Map {
    protected int[][] mMap;
    protected int mSize;

    public Map(int size) {
        mSize = size;
        mMap = new int[size][size];
    }

    public int[][] getMap() {
        return mMap;
    }

    public int getSize() {
        return mSize;
    }
}
