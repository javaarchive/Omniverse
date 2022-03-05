package io.github.javaarchive.omniverse.contexting;

import org.bukkit.entity.Player;

import io.github.javaarchive.omniverse.Omniverse;

import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;

public class PlayerContext {
    public Omniverse ov;
    
    public Player player;

    public Multiverse mv;
    public Universe uv;
    
    public boolean isOnline = false;

    public PlayerContext(Player player, Omniverse omniverse){
        this.player = player;
        this.ov = omniverse;
        this.isOnline = this.player.isOnline();
        if(this.isOnline){
            String worldName = this.player.getWorld().getName();
            if(this.ov.hasUniverse(worldName)){
                this.uv = this.ov.getUniverse(worldName);
                this.mv = this.ov.multiverseOf(this.uv);
            }
        }
    }
    
}
