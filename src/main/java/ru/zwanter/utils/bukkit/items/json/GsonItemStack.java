package ru.zwanter.utils.bukkit.items.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class GsonItemStack {

    @Getter
    private final static Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setObjectToNumberStrategy(JsonReader::nextInt)
                .registerTypeHierarchyAdapter(ConfigurationSerializable .class, new ConfigurationSerializableAdapter())
                .registerTypeHierarchyAdapter(ItemStack .class, new ItemStackAdapter())
                .create();

}
