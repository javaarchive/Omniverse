package io.github.javaarchive.omniverse.structures;

import java.util.ArrayList;
import java.util.List;

public class MultiverseUser {
    public MultiverseUser(){
        this.createdAt = System.currentTimeMillis();
    }
    
    List<String> roles = new ArrayList<>();

    public long createdAt = 0L;
}
