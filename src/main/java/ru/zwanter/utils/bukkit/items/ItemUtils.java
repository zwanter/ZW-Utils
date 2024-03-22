package ru.zwanter.utils.bukkit.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Set;

public class ItemUtils {

    public static boolean equalsName(ItemStack customItem, ItemStack eventItem) {
        return customItem.getItemMeta().getDisplayName().equals(eventItem.getItemMeta().getDisplayName());
    }

    public static boolean equalsCustomModelData(ItemStack customItem, ItemStack eventItem) {
        if (hasCustomModelData(customItem, eventItem)) {
            return customItem.getItemMeta().getCustomModelData() == eventItem.getItemMeta().getCustomModelData();
        }
        return false;
    }

    public static boolean equalsLore(ItemStack customItem, ItemStack eventItem) {
        if (hasLore(customItem, eventItem)) {
            return eventItem.getItemMeta().getLore().equals(customItem.getItemMeta().getLore());
        }
        return false;
    }

    public static boolean haveSameKeys(ItemStack customItem, ItemStack eventItem) {
        if (hasItemMeta(customItem, eventItem)) {
            PersistentDataContainer customItemContainer = customItem.getItemMeta().getPersistentDataContainer();
            PersistentDataContainer eventItemContainer = eventItem.getItemMeta().getPersistentDataContainer();

            Set<NamespacedKey> customItemKeys = customItemContainer.getKeys();
            Set<NamespacedKey> eventItemKeys = eventItemContainer.getKeys();

            for (NamespacedKey key : customItemKeys) {
                if (eventItemKeys.contains(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean hasLore(ItemStack customItem, ItemStack eventItem) {
        if (hasItemMeta(customItem, eventItem)) {
            return customItem.getItemMeta().getLore() != null &&
                    eventItem.getItemMeta().getLore() != null;
        }
        return false;
    }

    private static boolean hasItemMeta(ItemStack customItem, ItemStack eventItem) {
        return customItem.hasItemMeta() && eventItem.hasItemMeta();
    }

    private static boolean hasCustomModelData(ItemStack customItem, ItemStack eventItem) {
        if (hasItemMeta(customItem, eventItem)) {
            return customItem.getItemMeta().hasCustomModelData() && eventItem.getItemMeta().hasCustomModelData();
        }
        return false;
    }
    
}
