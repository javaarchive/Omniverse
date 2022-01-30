package io.github.javaarchive.omniverse.generation;


import io.github.javaarchive.omniverse.structures.UniverseOptions;
import io.github.javaarchive.omniverse.world.EmptyWorldGenerator;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class DefaultUniverseSetup {
    public static void create(String name, UniverseOptions uvOpts){
        WorldCreator wc = new WorldCreator(name).generateStructures(false).generator(new EmptyWorldGenerator()).type(WorldType.NORMAL).seed(0);

        wc.createWorld();
    }
}
