package io.github.javaarchive.omniverse.structures;

import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Multiverse {
    @Getter @Setter UUID owner;
    List<UUID> extraOwners = new ArrayList<>();

    public List<String> universes = new ArrayList<>();

    // Setting controls whether helpful messages will be shown on join
    boolean tutorialMode = true;
    // Only
    boolean useWhitelist = true;

    public boolean checkOwnership(UUID uuid){
        if(uuid.compareTo(this.owner) == 0){
            return true;
        }
        return this.extraOwners.contains(uuid);
    }
}
