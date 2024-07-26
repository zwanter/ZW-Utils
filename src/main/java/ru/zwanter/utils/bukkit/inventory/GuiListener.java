package ru.zwanter.utils.bukkit.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiListener implements Listener {

    public static void init(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new GuiListener(), plugin);
    }

    @EventHandler
    public void a(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof PluginInventory inventory) {
            inventory.whenClicked(event);
        }
    }

}
