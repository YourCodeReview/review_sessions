package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;

public class InInventoryItemState extends ItemState {
    private Inventory mInventory;

    public InInventoryItemState(Item item, Inventory inventory) {
        super(item);
        mInventory = inventory;
        mInventory.addItem(item);
    }

    @Override
    public ItemStateName onAddToInventory(Inventory inventory) {
        return ItemStateName.IN_INVENTORY;
    }

    @Override
    public ItemStateName onThrowAway(Inventory inventory) {
        inventory.removeItem(getItem());
        getItem().setState(new OnMapItemState(getItem()));
        return ItemStateName.ON_MAP;
    }

    @Override
    public ItemStateName getItemStateName() {
        return ItemStateName.IN_INVENTORY;
    }
}
