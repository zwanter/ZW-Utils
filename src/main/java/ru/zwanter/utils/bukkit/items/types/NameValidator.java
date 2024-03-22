package ru.zwanter.utils.bukkit.items.types;

import org.bukkit.inventory.ItemStack;
import ru.zwanter.utils.bukkit.items.ItemValidator;

import static ru.zwanter.utils.bukkit.items.ItemUtils.equalsName;


public class NameValidator implements ItemValidator {
    @Override
    public boolean isIt(ItemStack customItem, ItemStack eventItem) {
        return equalsName(customItem, eventItem);
    }
}