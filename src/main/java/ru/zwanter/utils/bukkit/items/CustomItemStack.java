package ru.zwanter.utils.bukkit.items;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;
import ru.zwanter.utils.bukkit.items.types.CustomStackEvent;

public class CustomItemStack implements Listener {

    protected final JavaPlugin plugin;

    @Getter
    private final ItemStack item;

    @Getter
    private final Consumer<CustomStackEvent> useRunnable;

    @Getter
    private final ItemValidator itemValidator;

    @Getter
    private final String permission;

    private CustomItemStack(JavaPlugin plugin, ItemStack item, Consumer<CustomStackEvent> useRunnable, ItemValidator itemValidator, String permission) {
        this.plugin = plugin;
        this.item = item;
        this.useRunnable = useRunnable;
        this.itemValidator = itemValidator;
        this.permission = permission;
        plugin.getServer().getPluginManager().registerEvents(new ItemStackEvent(this), plugin);
    }

    private boolean hasPermission(Player player) {
        if (permission == null) return true;
        return player.hasPermission(permission);
    }

    public static CustomItemBuilder builder() {
        return new CustomItemBuilder();
    }

    public static class CustomItemBuilder {

        private JavaPlugin plugin;
        private ItemStack item;
        private Consumer<CustomStackEvent> useRunnable;
        private ItemValidator itemValidator;
        private String permission;

        public CustomItemBuilder() {

        }

        public CustomItemBuilder setPlugin(JavaPlugin plugin) {
            this.plugin = plugin;
            return this;
        }
        public CustomItemBuilder setItem(ItemStack item) {
            this.item = item;
            return this;
        }
        public CustomItemBuilder setPermission(String permission) {
            this.permission = permission;
            return this;
        }
        public CustomItemBuilder setValidator(ItemValidator itemValidator) {
            this.itemValidator = itemValidator;
            return this;
        }
        public CustomItemBuilder setUseRunnable(Consumer<CustomStackEvent> useRunnable) {
            this.useRunnable = useRunnable;
            return this;
        }
        public CustomItemStack build() {
            return new CustomItemStack(plugin, item, useRunnable, itemValidator, permission);
        }

    }

    protected static class ItemStackEvent implements Listener {

        private final CustomItemStack customItemStack;

        public ItemStackEvent(CustomItemStack customItemStack) {
            this.customItemStack = customItemStack;
        }

        @EventHandler
        public void onClick(PlayerInteractEvent event) {
            Player player = event.getPlayer();

            if (!customItemStack.hasPermission(player)) return;

            ItemStack item = event.getItem();

            if (item == null) return;

            ItemValidator itemValidatorStack = customItemStack.getItemValidator();

            if (itemValidatorStack.isIt(customItemStack.item, item)) {
                customItemStack.useRunnable.accept(new CustomStackEvent(event.getPlayer(), event.getAction(), event.getItem(), event.getClickedBlock()));
            }
        }

        @EventHandler
        public void onClick(PlayerInteractEntityEvent event) {
            Player player = event.getPlayer();

            if (!customItemStack.hasPermission(player)) return;

            ItemStack item = player.getItemInHand();

            ItemValidator itemValidatorStack = customItemStack.getItemValidator();

            if (itemValidatorStack.isIt(customItemStack.item, item)) {
                customItemStack.useRunnable.accept(new CustomStackEvent(player, event.getRightClicked(), event.getHand()));
            }
        }
    }
}
