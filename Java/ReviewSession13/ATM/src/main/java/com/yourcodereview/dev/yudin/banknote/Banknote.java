package com.yourcodereview.dev.yudin.banknote;

public enum Banknote {

    BANKNOTE_1000(1000),
    BANKNOTE_500(500),
    BANKNOTE_200(200),
    BANKNOTE_100(100),
    BANKNOTE_50(50),
    BANKNOTE_20(20),
    BANKNOTE_10(10),
    BANKNOTE_5(5),
    BANKNOTE_2(2),
    BANKNOTE_1(1);

    private int banknote;

    public int getBanknote() {
        return banknote;
    }

    Banknote(int banknote) {
        this.banknote = banknote;
    }
}

