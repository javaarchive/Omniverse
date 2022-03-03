package io.github.javaarchive.omniverse.structures;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.javaarchive.omniverse.Omniverse;

public class Universe {
    @Getter @Setter UUID owner;
    public List<UUID> extraOwners = new ArrayList<>();

    @Setter @Getter String parentMultiverseName;

    public boolean checkOwnership(UUID uuid){
        if(uuid.compareTo(this.owner) == 0){
            return true;
        }
        return this.extraOwners.contains(uuid);
    }
}
