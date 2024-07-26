package ru.zwanter.utils.bukkit.inventory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class PluginInventory implements InventoryHolder {
    @Getter
    protected final Player player;
    protected final JavaPlugin plugin;

    public PluginInventory(@NotNull Player player, JavaPlugin plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public abstract @NotNull Inventory getInventory();

    public abstract void whenClicked(InventoryClickEvent event);

    public void open() {
        if (Bukkit.isPrimaryThread())
            player.openInventory(getInventory());
        else
            Bukkit.getScheduler().runTask(plugin, () -> player.openInventory(getInventory()));
    }
}
