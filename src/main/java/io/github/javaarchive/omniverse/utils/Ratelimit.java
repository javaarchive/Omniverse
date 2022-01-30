package io.github.javaarchive.omniverse.utils;

import org.bukkit.Bukkit;

import java.util.*;

public class Ratelimit {
    long time;

    public Ratelimit(long time){
        this.time = time;
    }

    Map<UUID, Long> userLast = new HashMap<>();

    public void purge(){
        userLast.forEach((playerUUID,time) -> {
            if(!Bukkit.getPlayer(playerUUID).isOnline()){
                userLast.remove(playerUUID);
            }
        });
    }

    public boolean check(UUID u){
        if(userLast.containsKey(u) && (System.currentTimeMillis() - userLast.get(u)) < this.time){
            return false;
        }
        this.userLast.put(u, System.currentTimeMillis());
        return true;
    }

    public long get(UUID u){
        if(userLast.containsKey(u) && (userLast.get(u) - System.currentTimeMillis()) < this.time){
            return (userLast.get(u) - System.currentTimeMillis());
        }
        return 0L;
    }
}
