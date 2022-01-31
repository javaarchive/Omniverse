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
    @Getter @Setter boolean tutorialMode = true;
    // Whitelist declares only people explictly allowed or owners are allowed in
    @Getter @Setter boolean useWhitelist = true;
    @Getter List<UUID> whitelisted = new ArrayList<>();

    public boolean checkOwnership(UUID uuid){
        if(uuid.compareTo(this.owner) == 0){
            return true;
        }
        return this.extraOwners.contains(uuid);
    }

    public boolean canJoin(UUID uuid){
        if(!isUseWhitelist()){
            return true;
        }else{
            return checkOwnership(uuid) || whitelisted.contains(uuid);
        }
    }
}
