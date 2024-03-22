package ru.zwanter.utils.bukkit.items.types;

import org.bukkit.inventory.ItemStack;
import ru.zwanter.utils.bukkit.items.ItemValidator;

import static ru.zwanter.utils.bukkit.items.ItemUtils.equalsLore;

public class LoreValidator implements ItemValidator {
    @Override
    public boolean isIt(ItemStack customItem, ItemStack eventItem) {
        return equalsLore(customItem, eventItem);
    }
}