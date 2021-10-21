package ru.hawoline.alonar.model.personage.effect;

import ru.hawoline.alonar.model.personage.specification.attribute.AttributeName;

import java.io.Serializable;

public class Effect implements Serializable {
    private AttributeName mAttributeName;
    private int mValue;

    private static final long serialVersionUID = -543847832486841389L;

    public Effect(AttributeName attributeName, int value) {
        mAttributeName = attributeName;
        mValue = value;
    }

    public AttributeName getAttributeName() {
        return mAttributeName;
    }

    public void setAttributeName(AttributeName attributeName) {
        mAttributeName = attributeName;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }
}
