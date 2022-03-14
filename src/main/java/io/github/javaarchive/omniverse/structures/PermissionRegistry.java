package io.github.javaarchive.omniverse.structures;

import java.util.*;

public class PermissionRegistry {
    Set<String> perms;

    public PermissionRegistry(){
        this.perms = new HashSet<>();
    }

    public void addPerm(String perm){
        this.perms.add(perm);
    }

    public boolean hasPerm(String perm){
        return this.perms.contains(perm);
    }

    public void addMultiplePerms(String... perms){
        this.perms.addAll(Arrays.asList(perms));
    }

    public void clearAll(){
        this.perms.clear();
    }
}
