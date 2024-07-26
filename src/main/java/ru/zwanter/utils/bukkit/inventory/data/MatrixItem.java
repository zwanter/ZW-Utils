package ru.zwanter.utils.bukkit.inventory.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Setter
@Getter
public class MatrixItem {
    private char key;
    private ItemStack value;

    public MatrixItem(char key, ItemStack value) {
        this.key = key;
        this.value = value;
    }

}
