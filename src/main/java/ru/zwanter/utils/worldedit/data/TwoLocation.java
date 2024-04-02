package ru.zwanter.utils.worldedit.data;

import lombok.Getter;
import org.bukkit.Location;

@Getter
public class TwoLocation {

    private final Location loc1;
    private final Location loc2;

    public TwoLocation(Location loc1, Location loc2) {
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public boolean isNullable(){
        return loc1 != null && loc2 != null;
    }
}