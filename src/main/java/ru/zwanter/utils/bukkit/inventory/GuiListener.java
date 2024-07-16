package ru.zwanter.utils.bukkit.inventory;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.entity.Entity;
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
        if (event.getInventory().getHolder() instanceof PluginInventory) {
            PluginInventory inventory = (PluginInventory) event.getInventory().getHolder();
            inventory.whenClicked(event);

            Object o = event.getWhoClicked();

            Entity entity = (Entity) o;

            

        }
    }

}
