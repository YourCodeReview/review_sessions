package ru.hawoline.alonar.model.personage.item.equipment.state;

import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.equipment.Equipment;
import ru.hawoline.alonar.model.personage.item.state.ItemState;
import ru.hawoline.alonar.model.personage.item.state.ItemStateName;

public abstract class EquipmentState extends ItemState {
    private Equipment mEquipment;

    public EquipmentState(Equipment equipment) {
        super(equipment);
        mEquipment =  equipment;
    }

    @Override
    public abstract ItemStateName onEquip(Personage personage);

    @Override
    public abstract ItemStateName onUnequip(Personage personage);

    @Override
    public Equipment getItem() {
        return mEquipment;
    }
}
