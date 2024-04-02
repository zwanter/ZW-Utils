package ru.zwanter.utils.worldedit;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import ru.zwanter.utils.worldedit.data.TwoLocation;

public class WorldEditUtils {

    public static TwoLocation getSelectionPoints(Player player) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        if (worldEdit != null) {
            try {
                Region selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());

                if (selection != null) {
                    World world = Bukkit.getWorld(selection.getWorld().getName());

                    BlockVector3 minVector = selection.getMinimumPoint();
                    BlockVector3 maxVector = selection.getMaximumPoint();

                    Location min = new Location(world, minVector.getX(), minVector.getY(), minVector.getZ());
                    Location max = new Location(world, maxVector.getX(), maxVector.getY(), maxVector.getZ());

                    return new TwoLocation(min, max);
                }
            } catch (Exception e) {
                Bukkit.getLogger().warning(e.getMessage());
            }
        }
        return new TwoLocation(null, null);
    }

    private static BlockVector3 convertLocationToVector(Location location) {
        return BukkitAdapter.asBlockVector(location);
    }

    public static Clipboard copyRegion(TwoLocation twoLocation) {

        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        Location location1 = twoLocation.getLoc1();
        Location location2 = twoLocation.getLoc2();
        BlockVector3 vec1 = convertLocationToVector(location1);
        BlockVector3 vec2 = convertLocationToVector(location2);
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(location1.getWorld());
        CuboidRegion region = new CuboidRegion(world, vec1, vec2);
        Clipboard clipboard;

        try (EditSession editSession = worldEdit.getWorldEdit().getEditSessionFactory().getEditSession(world, -1)) {
            clipboard = new BlockArrayClipboard(region);
            ForwardExtentCopy copy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
            Operations.complete(copy);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

        return clipboard;
    }

    public static void pasteClipboard(Clipboard clipboard, Location pasteLocation) {
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        BlockVector3 to = convertLocationToVector(pasteLocation);
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(pasteLocation.getWorld());

        try (EditSession editSession = worldEdit.getWorldEdit().getEditSessionFactory().getEditSession(world, -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(to)
                    .ignoreAirBlocks(false)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyPaste(TwoLocation twoLocation, Location newLocation) {
        try {
            Clipboard clipboard = copyRegion(twoLocation);
            pasteClipboard(clipboard, newLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
