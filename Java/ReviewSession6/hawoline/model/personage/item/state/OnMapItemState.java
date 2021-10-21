package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;

public class OnMapItemState extends ItemState {
    public OnMapItemState(Item item) {
        super(item);
    }

    @Override
    public ItemStateName onAddToInventory(Inventory inventory) {
        if (inventory.hasFreeSpace()) {
            getItem().setState(new InInventoryItemState(getItem(), inventory));
            return ItemStateName.IN_INVENTORY;
        }

        return getItemStateName();
    }

    @Override
    public ItemStateName onThrowAway(Inventory inventory) {
        return getItemStateName();
    }

    @Override
    public ItemStateName getItemStateName() {
        return ItemStateName.ON_MAP;
    }
}
