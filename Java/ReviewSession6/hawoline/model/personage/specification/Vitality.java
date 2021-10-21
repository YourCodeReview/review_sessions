package ru.hawoline.alonar.model.personage.specification;

import ru.hawoline.alonar.util.Pair;

import java.io.Serializable;

public class Vitality implements Serializable {
    private VitalityType mType;
    private Pair<Integer, Integer> mResidualMax;

    private static final long serialVersionUID = -990341370978716524L;

    public static final int HP = 0;
    public static final int MP = 1;

    public Vitality(VitalityType type, int max) {
        setVitality(type, new Pair<>(max, max));
    }

    public VitalityType getType() {
        return mType;
    }

    public Pair<Integer, Integer> getResidualMax() {
        return mResidualMax;
    }

    public void setVitality(VitalityType type, Pair<Integer, Integer> residualMax) {
        mType = type;
        mResidualMax = residualMax;
    }

    public void setMaxValue(int maxValue) {
        mResidualMax.setFirst(maxValue);
        mResidualMax.setSecond(maxValue);
    }

    public void setValue(int value) {
        if (value > getResidualMax().getFirst()) {
            setMaxValue(value);
        } else {
            mResidualMax.setSecond(value);
        }
    }
}
