package ru.hawoline.alonar.view;

public interface MainView extends View {

    void showEnemiesList(int slotIndex);

    void removeEnemyTextView();

    void render();
}
