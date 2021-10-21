package ru.hawoline.alonar.model;

import java.io.Serializable;

public class Range implements Serializable {
    private int mLow;
    private int mHigh;
    private int mDistance;

    private static final long serialVersionUID = -3928741113671976358L;

    public Range(int low, int high) {
        mLow = low;
        mHigh = high;
        setDistance();
    }

    public int getLow() {
        return mLow;
    }

    public void setLow(int low) {
        mLow = low;
        setDistance();
    }

    public int getHigh() {
        return mHigh;
    }

    public void setHigh(int high) {
        mHigh = high;
        setDistance();
    }

    public int getDistance() {
        return mDistance;
    }

    private void setDistance() {
        mDistance = Math.abs(mHigh - mLow);
    }
}
