package ru.zwanter.utils.bukkit.items.types;

import org.bukkit.inventory.ItemStack;
import ru.zwanter.utils.bukkit.items.ItemValidator;

import static ru.zwanter.utils.bukkit.items.ItemUtils.haveSameKeys;

public class PDCValidator implements ItemValidator {
    @Override
    public boolean isIt(ItemStack customItem, ItemStack eventItem) {
        return haveSameKeys(customItem, eventItem);
    }
}
