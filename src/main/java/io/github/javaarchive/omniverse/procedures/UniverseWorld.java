package io.github.javaarchive.omniverse.procedures;

import io.github.javaarchive.omniverse.generation.DefaultUniverseSetup;
import io.github.javaarchive.omniverse.structures.UniverseOptions;

public class UniverseWorld {
    public static void generateNew(String name, String[] args){
        DefaultUniverseSetup.create(name, new UniverseOptions());
    }
}
