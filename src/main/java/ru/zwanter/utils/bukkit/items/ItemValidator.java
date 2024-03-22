package ru.zwanter.utils.bukkit.items;

import org.bukkit.inventory.ItemStack;
import ru.zwanter.utils.bukkit.items.types.CustomModelDataValidator;
import ru.zwanter.utils.bukkit.items.types.LoreValidator;
import ru.zwanter.utils.bukkit.items.types.NameValidator;
import ru.zwanter.utils.bukkit.items.types.PDCValidator;


public interface ItemValidator {

    ItemValidator NAME_VALIDATOR = new NameValidator();
    ItemValidator CUSTOM_MODEL_DATA_VALIDATOR = new CustomModelDataValidator();
    ItemValidator LORE_VALIDATOR = new LoreValidator();
    ItemValidator PDC_VALIDATOR = new PDCValidator();
    
    boolean isIt(ItemStack customItem, ItemStack eventItem);
    
}
