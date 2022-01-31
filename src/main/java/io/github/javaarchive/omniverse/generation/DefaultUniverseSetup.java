package io.github.javaarchive.omniverse.generation;


import io.github.javaarchive.omniverse.structures.UniverseOptions;
import io.github.javaarchive.omniverse.world.EmptyWorldGenerator;
import org.bukkit.*;

public class DefaultUniverseSetup {
    public static void create(String name, UniverseOptions uvOpts){
        WorldCreator wc = new WorldCreator(name).generateStructures(false).generator(new EmptyWorldGenerator()).type(WorldType.NORMAL).seed(0);

        World world = wc.createWorld();
        world.setSpawnLocation(0,90,0);
        Location l = world.getSpawnLocation();
        Location spawnBlockLoc = l.subtract(0,10,0);
        world.getBlockAt(spawnBlockLoc).setType(Material.GLASS);
    }
}
