package io.github.javaarchive.omniverse.contexting;

import org.bukkit.entity.Player;

import io.github.javaarchive.omniverse.Omniverse;

import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;

public class PlayerContext {
    Omniverse ov;
    
    Player player;

    Multiverse mv;
    Universe uv;
    
    public boolean isOnline = false;

    public PlayerContext(Player player, Omniverse omniverse){
        this.player = player;
        this.ov = omniverse;
        this.isOnline = this.player.isOnline();
        if(this.isOnline){
            if(this.m){

            }
        }
    }
    
}
