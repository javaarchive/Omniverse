package io.github.javaarchive.omniverse.structures;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MultiverseUser {
    public MultiverseUser(){
        this.createdAt = System.currentTimeMillis();
    }
    
    @Getter List<String> roles = new ArrayList<>();

    public long createdAt = 0L;

    // TODO: Persist Inventory?

    public static void initializeUser(MultiverseUser mu){
        mu.createdAt = System.currentTimeMillis();
    }
}
