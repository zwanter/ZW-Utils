package ru.zwanter.utils.bukkit.items.json;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GsonItemStack {

    @Getter
    private final static Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setObjectToNumberStrategy(JsonReader::nextInt)
                .registerTypeHierarchyAdapter(ConfigurationSerializable .class, new ConfigurationSerializableAdapter())
                .registerTypeHierarchyAdapter(ItemStack .class, new ItemStackAdapter())
                .create();

    public static JsonElement itemToJson(ItemStack itemStack) {
        return gson.toJsonTree(itemStack, ItemStack.class);
    }

    public static ItemStack jsonToItem(JsonElement jsonElement) {
        return gson.fromJson(jsonElement, ItemStack.class);
    }

    public static JsonObject itemsToJson(List<ItemStack> itemStacks) {
        JsonObject mainObject = new JsonObject();

        JsonArray jsonArray = new JsonArray();
        for (ItemStack itemStack : itemStacks) {
            jsonArray.add(gson.toJsonTree(itemStack, ItemStack.class));
        }

        mainObject.add("items", jsonArray);

        return mainObject;
    }

    public static List<ItemStack> itemsFromJson(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("items");

        if (jsonArray == null) {
            throw new IllegalArgumentException("JsonArray 'items' not found in JsonObject");
        }

        List<ItemStack> itemStacks = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement != null) {
                ItemStack itemStack = gson.fromJson(jsonElement, ItemStack.class);
                itemStacks.add(itemStack);
            }
        }

        return itemStacks;
    }

}
