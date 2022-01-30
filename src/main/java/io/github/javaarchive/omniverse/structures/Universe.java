package io.github.javaarchive.omniverse.structures;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Universe {
    @Getter @Setter UUID owner;
    public List<UUID> extraOwners = new ArrayList<>();


}
