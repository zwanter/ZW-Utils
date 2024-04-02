package ru.zwanter.utils.mv;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

public class MultiverseCoreUtils {
    private static final MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");;

    public static World createEmptyWorld(String worldName) {
        if (multiverseCore == null) {
            Bukkit.getLogger().warning("MultiverseCore is not initialized!");
            return null;
        }

        MVWorldManager wm = multiverseCore.getMVWorldManager();
        if (wm.isMVWorld(worldName)) {
            Bukkit.getLogger().warning("World already exists!");
            return wm.getMVWorld(worldName).getCBWorld();
        }

        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generator("VoidGenerator");

        if (wm.addWorld(worldName, World.Environment.NORMAL, null, WorldType.FLAT, false, "VoidGenerator", true)) {
            World world = Bukkit.getWorld(worldName);
            if (world != null) {

                world.setSpawnFlags(false, false);
                world.setAnimalSpawnLimit(0);
                world.setMonsterSpawnLimit(0);
                world.setWaterAnimalSpawnLimit(0);
                world.setAmbientSpawnLimit(0);
                Bukkit.getLogger().info("Fully empty flat world " + worldName + " has been created successfully!");
                return world;
            } else {
                Bukkit.getLogger().warning("World is created but cannot be accessed!");
            }
        } else {
            Bukkit.getLogger().warning("Failed to create world " + worldName);
        }
        return null;
    }

    public static boolean deleteWorld(String worldName) {
        if (multiverseCore == null) {
            Bukkit.getLogger().warning("MultiverseCore is not initialized!");
            return false;
        }

        MVWorldManager wm = multiverseCore.getMVWorldManager();
        if (!wm.isMVWorld(worldName)) {
            Bukkit.getLogger().warning("World " + worldName + " does not exist!");
            return false;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Bukkit.getLogger().warning("The world " + worldName + " could not be accessed!");
            return false;
        }

        World mainWorld = Bukkit.getServer().getWorlds().get(0);
        if (mainWorld == null) {
            Bukkit.getLogger().warning("The main world could not be accessed!");
            return false;
        }

        for (Player player : world.getPlayers()) {
            player.teleport(mainWorld.getSpawnLocation());
            player.sendMessage("You have been teleported to the main world due to the deletion of " + worldName);
        }

        boolean deleteSuccessful = wm.deleteWorld(worldName);
        if (deleteSuccessful) {
            Bukkit.getLogger().info("World " + worldName + " has been deleted successfully!");
        } else {
            Bukkit.getLogger().warning("Failed to delete world " + worldName + ".");
        }
        return deleteSuccessful;
    }
}
