package ru.hawoline.alonar.model.personage.specification.attribute;

import java.io.Serializable;

public class PersonageAttribute implements Serializable {
    private int mValue;
    private int mMax;

    private static final long serialVersionUID = 5405874440905936295L;

    public PersonageAttribute(int max) {
        mValue = max;
        mMax = max;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        if (value < mMax) {
            mValue = value;
        }
    }

    public void increase(int value) {
        mMax += value;
        mValue += value;
    }

    public void decrease(int value) {
        mValue -= value;
        if (mValue < 0) {
            mValue = 0;
        }
        mMax -= value;
        if (mMax < 0) {
            mMax = 0;
        }
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
        if (mMax < mValue) {
            mValue = mMax;
        }
    }
}
