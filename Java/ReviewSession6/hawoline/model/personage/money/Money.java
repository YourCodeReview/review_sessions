package ru.hawoline.alonar.model.personage.money;

import java.io.Serializable;

public class Money implements Serializable {
    private int mMoney;
    private int mCopper;
    private int mSilver;
    private int mGold;
    private int mDoubloon;

    private static final long serialVersionUID = -3731504861208603753L;

    public Money(int money, int doubloon) {
        mMoney = money;
        mDoubloon = doubloon;
    }

    public int getMoney() {
        return mMoney;
    }

    public void setMoney(int money) {
        mMoney = money;
        mCopper = mMoney % 100;
        mSilver = mMoney / 100 % 100;
        mGold = mMoney / 10000;
    }

    public int getDoubloon() {
        return mDoubloon;
    }

    public void setDoubloon(int doubloon) {
        mDoubloon = doubloon;
    }

    public int getCopper() {
        return mCopper;
    }

    public int getSilver() {
        return mSilver;
    }

    public int getGold() {
        return mGold;
    }
}
