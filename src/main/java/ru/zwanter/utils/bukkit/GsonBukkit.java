package ru.zwanter.utils.bukkit;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.zwanter.utils.bukkit.items.json.ConfigurationSerializableAdapter;
import ru.zwanter.utils.bukkit.items.json.ItemStackAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GsonBukkit {

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
            return new ArrayList<>();
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

    public static List<Player> jsonToPlayersUUID(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("players-uuid");

        if (jsonArray == null) {
            return new ArrayList<>();
        }

        List<Player> players = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement != null) {
                players.add(Bukkit.getPlayer(UUID.fromString(jsonElement.toString())));
            }
        }

        return players;
    }

    public static JsonObject playersToJsonUUID(List<Player> players) {
        JsonObject mainObject = new JsonObject();

        JsonArray jsonArray = new JsonArray();
        for (Player player : players) {
            jsonArray.add(player.getUniqueId().toString());
        }

        mainObject.add("players-uuid", jsonArray);

        return mainObject;
    }

    public static List<Player> jsonToPlayersNickname(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("players-nicknames");

        if (jsonArray == null) {
            return new ArrayList<>();
        }

        List<Player> players = new ArrayList<>();

        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement != null) {
                players.add(Bukkit.getPlayer(jsonElement.toString()));
            }
        }

        return players;
    }

    public static JsonObject playersToJsonNickname(List<Player> players) {
        JsonObject mainObject = new JsonObject();

        JsonArray jsonArray = new JsonArray();
        for (Player player : players) {
            jsonArray.add(player.getName());
        }

        mainObject.add("players-nicknames", jsonArray);

        return mainObject;
    }

}
