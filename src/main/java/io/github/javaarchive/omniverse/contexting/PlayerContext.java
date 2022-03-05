package io.github.javaarchive.omniverse.contexting;

import io.github.javaarchive.omniverse.structures.MultiverseUser;
import io.github.javaarchive.omniverse.structures.Permission;
import lombok.Getter;
import org.bukkit.entity.Player;

import io.github.javaarchive.omniverse.Omniverse;

import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;

import java.util.ArrayList;
import java.util.List;

public class PlayerContext {
    public Omniverse ov;
    
    public Player player;

    public Multiverse mv;
    public Universe uv;

    private @Getter String uvName;
    
    public boolean isOnline = false;

    public PlayerContext(Player player, Omniverse omniverse){
        this.player = player;
        this.ov = omniverse;
        this.isOnline = this.player.isOnline();
        if(this.isOnline){
            String worldName = this.player.getWorld().getName();
            if(this.ov.hasUniverse(worldName)){
                this.uv = this.ov.getUniverse(worldName);
                this.uvName = worldName;
                this.mv = this.ov.multiverseOf(this.uv);
            }
        }
    }

    public MultiverseUser getMemberData(){
        return this.ov.getMultiverseUserProfile(this.uv.getParentMultiverseName(), this.player.getUniqueId());
    }

    public Permission checkPerm(String name){
        List<String> roles = this.getMemberData().getRoles();
        Permission masterPerms = this.mv.perms.getPermWithRoles(roles,name);
        Permission univPerms = this.mv.perms.getPermWithRoles(roles,this.uvName + "." + name);

        if(univPerms != Permission.NETURAL){
            return univPerms;
        }else{
            return masterPerms;
        }
    }
}
