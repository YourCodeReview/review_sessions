package ru.hawoline.alonar.model.personage.item.equipment;

// Класс находится в экипировке, потому что я не буду использовать тело в других частях приложения кроме как
// частей экипировки персонажа. И не буду реализовывать логику получения урона рукой, или ногой и отрывания частей тела.
public enum Body {
    HEAD, RIGHT_ARM, LEFT_ARM, SHOULDERS, BACK, BODY, BELT, LEG, SHOES, ARMS, BRACELET, RING1, RING2, EARRING1,
    EARRING2, NECK
}
