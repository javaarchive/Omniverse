package io.github.javaarchive.omniverse.contexting;

import io.github.javaarchive.omniverse.structures.MultiverseUser;
import io.github.javaarchive.omniverse.structures.Permission;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
    
    @Getter @Setter private boolean online = false;

    @Getter @Setter private boolean multiversedWorld = false;

    public PlayerContext(Player player){
        this(player, Omniverse.getInstance());
    }

    public PlayerContext(Player player, Omniverse omniverse){
        this.player = player;
        this.ov = omniverse;
        this.setMultiversedWorld(this.player.isOnline());
        this.setOnline(player.isOnline());
        if(this.isOnline()){
            String worldName = this.player.getWorld().getName();
            if(this.ov.hasUniverse(worldName)){
                this.uv = this.ov.getUniverse(worldName);
                this.uvName = worldName;
                this.mv = this.ov.multiverseOf(this.uv);

                this.setMultiversedWorld(true);
            }else{
                this.setMultiversedWorld(false);
            }
        }else{
            this.setMultiversedWorld(false);
        }
    }

    public MultiverseUser getMemberData(){
        return this.ov.getMultiverseUserProfile(this.uv.getParentMultiverseName(), this.player.getUniqueId());
    }

    public void setMemberData(MultiverseUser mu){
        this.ov.setMultiverseUserProfile(this.uv.getParentMultiverseName(), this.player.getUniqueId(), mu);
    }

    public Permission checkPerm(String name){

        if(!this.isMultiversedWorld()){
            return Permission.NETURAL;
        }

        List<String> roles = this.getMemberData().getRoles();

        Permission masterPerms = this.mv.perms.getPermWithRoles(roles,name);
        Permission univPerms = this.mv.perms.getPermWithRoles(roles,this.uvName + "." + name);

        if(univPerms != Permission.NETURAL){
            return univPerms;
        }else{
            return masterPerms;
        }
    }

    public String toString(){
        return "[PlayerContext username="+this.player.getName() + " isMultiverse="+this.isMultiversedWorld() + " mv=" + this.mv + " uv=" + this.uv + "]";
    }

    public static PlayerContext fromOffline(String name){
        OfflinePlayer oPlayer = Bukkit.getPlayer(name);
        return (new PlayerContext((Player) oPlayer));
    }

    public void test(){
        // Test code and autocomplete test
        
    }
}
