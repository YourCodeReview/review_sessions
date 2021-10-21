package ru.hawoline.alonar.model.personage.item.state;

import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.inventory.Inventory;
import ru.hawoline.alonar.model.personage.item.Item;

/**
 * Состояния:
 * 1. На карте;
 * 2. В инвентаре;
 *
 * Для экипировки еще:
 * 3. На персонаже;
 *
 * Для расходников еще:
 * 4. Использован;
 *
 * Переходы:
 * 1. (Валяется на дороге || в трупе(группа предметов)) <-> Инвентарь <-> Экипирован
 * 2. (Валяется на дороге || в трупе(группа предметов)) <-> Инвентарь -> Использован(исчез, для расходников)
 *
 * Действия с предметами:
 * 1. Положить в инвентарь;
 * 2. Выбросить n-ое количество предметов;
 * 3. Надеть(для снаряжения);
 * 4. Снять снаряжение в инвентарь(для снаряжения);
 * 5. Использовать(для зелий и других расходников);
 */
public abstract class ItemState {
    private Item mItem;

    public ItemState(Item item) {
        mItem = item;
    }

    public abstract ItemStateName onAddToInventory(Inventory inventory);
    public abstract ItemStateName onThrowAway(Inventory inventory);
    protected ItemStateName onEquip(Personage personage) {
        return getItemStateName();
    }
    protected ItemStateName onUnequip(Personage personage) {
        return getItemStateName();
    }
    public abstract ItemStateName getItemStateName();
    public Item getItem() {
        return mItem;
    }
}
