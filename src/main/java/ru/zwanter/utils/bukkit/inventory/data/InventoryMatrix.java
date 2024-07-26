package ru.zwanter.utils.bukkit.inventory.data;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.zwanter.utils.bukkit.inventory.PluginInventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InventoryMatrix {

    /**
     InventoryMatrix inventoryMatrix = InventoryMatrix.builder()
         .setHolder(null)
         .setLines("A|B|C|D|",
                 "|A|B|C|D|",
                 "A|B|C|D|")
         .setName("InventoryName")
         .addMatrixItem(new MatrixItem('|', null))
         .build();

     inventoryMatrix.getInventory();
     */

    private char[][] matrix;

    private HashMap<Character, ItemStack> items;

    @Getter
    private Inventory inventory;

    private String name;

    private PluginInventory holder;


    private InventoryMatrix(HashMap<Character, ItemStack> items, String[] lines, String name, PluginInventory holder) {
        this.items = items;
        if (lines.length >= 6) {
            matrix = new char[6][9];
            for (int i = 0; i < 6; i++) {
                for (int d = 0; d < 9; d++) {
                    if (d < lines[i].length()) {
                        matrix[i][d] = lines[i].charAt(d);
                    } else {
                        matrix[i][d] = ' ';
                    }
                }
            }
        } else {
            matrix = new char[lines.length][9];
            for (int i = 0; i < lines.length; i++) {
                for (int d = 0; d < 9; d++) {
                    if (d < lines[i].length()) {
                        matrix[i][d] = lines[i].charAt(d);
                    } else {
                        matrix[i][d] = ' ';
                    }
                }
            }
        }

        this.name = name;
        this.holder = holder;

        this.inventory = Bukkit.createInventory(holder, matrix.length * 9, name);

        fillInventory();
    }

    public Inventory setName(String name) {
        this.name = name;
        this.inventory = Bukkit.createInventory(holder, matrix.length * 9, name);
        fillInventory();
        return this.inventory;
    }

    private void fillInventory() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char key = matrix[i][j];
                if (key != ' ' && items.containsKey(String.valueOf(key))) {
                    this.inventory.setItem(i * 9 + j, items.get(String.valueOf(key)));
                } else {
                    this.inventory.setItem(i * 9 + j, null);
                }
            }
        }
    }

    public static InventoryMatrixBuilder builder() {
        return new InventoryMatrixBuilder();
    }

    public static class InventoryMatrixBuilder {

        private String[] lines;

        private HashMap<Character, ItemStack> items;

        private String name;

        private PluginInventory holder;

        public InventoryMatrixBuilder() {
            items = new HashMap<>();
        }

        public InventoryMatrixBuilder setLines(String... lines) {
            this.lines = lines;
            return this;
        }

        public InventoryMatrixBuilder setLines(List<String> lines) {
            this.lines = lines.toArray(new String[lines.size()]);
            return this;
        }

        public InventoryMatrixBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public InventoryMatrixBuilder setHolder(PluginInventory holder) {
            this.holder = holder;
            return this;
        }

        public InventoryMatrixBuilder addMatrixItem(MatrixItem... matrixItems) {
            Arrays.stream(matrixItems).forEach(matrixItem -> {items.put(matrixItem.getKey(), matrixItem.getValue());});
            return this;
        }

        public InventoryMatrixBuilder addMatrixItem(List<MatrixItem> matrixItems) {
            matrixItems.forEach(matrixItem -> {items.put(matrixItem.getKey(), matrixItem.getValue());});
            return this;
        }

        public InventoryMatrix build() {
            return new InventoryMatrix(items, lines, name, holder);
        }

    }

}
