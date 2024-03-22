package ru.zwanter.utils.bukkit.items.types;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class CustomStackEvent {

    private final Player who;
    private final ItemStack item;

    private Entity clickedEntity;
    private EquipmentSlot hand;

    private Action action;
    private Block clickedBlock;


    public CustomStackEvent(@NotNull Player who, @NotNull Entity clickedEntity, @NotNull EquipmentSlot hand) {
        this.who = who;
        this.clickedEntity = clickedEntity;
        this.hand = hand;
        this.item = who.getItemInHand();
    }

    public CustomStackEvent(@NotNull Player who, @NotNull Action action, @Nullable ItemStack item, @Nullable Block clickedBlock) {
        this.who = who;
        this.action = action;
        this.item = item;
        this.clickedBlock = clickedBlock;
    }
}
