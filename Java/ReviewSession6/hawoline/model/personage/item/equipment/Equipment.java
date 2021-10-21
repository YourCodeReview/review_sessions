package ru.hawoline.alonar.model.personage.item.equipment;

import androidx.annotation.NonNull;
import ru.hawoline.alonar.model.personage.effect.Effect;
import ru.hawoline.alonar.model.personage.item.Item;
import ru.hawoline.alonar.model.personage.item.Quality;
import ru.hawoline.alonar.model.personage.money.Gem;
import ru.hawoline.alonar.util.Pair;

import java.util.ArrayList;

public abstract class Equipment extends Item {
    protected Pair<Integer, Integer> mStrength;
    protected ArrayList<Gem> mGems;
    protected ArrayList<Effect> mEffects;
    protected Body mRequiredBody;

    private static final long serialVersionUID = -1066374173398793188L;

    public Equipment(String name, int requiredLevel, Quality quality, Pair<Integer, Integer> strength, Body requiredBody) {
        super(name, requiredLevel, quality);
        mStrength = strength;
        mRequiredBody = requiredBody;
        mGems = new ArrayList<>();
        mEffects = new ArrayList<>();
    }

    public Pair<Integer, Integer> getStrength() {
        return mStrength;
    }

    public void setStrength(Pair<Integer, Integer> strength) {
        mStrength = strength;
    }

    public ArrayList<Gem> getGems() {
        return mGems;
    }

    public void setGems(ArrayList<Gem> gems) {
        mGems = gems;
    }

    public void addGem(Gem gem) {
        mGems.add(gem);
    }

    @NonNull
    public ArrayList<Effect> getEffects() {
        return mEffects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        mEffects = effects;
    }

    public void addEffect(Effect effect) {
        mEffects.add(effect);
    }

    public Body getRequiredBody() {
        return mRequiredBody;
    }

    public void setRequiredBody(Body requiredBody) {
        mRequiredBody = requiredBody;
    }
}
