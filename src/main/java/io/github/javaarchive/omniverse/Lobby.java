package io.github.javaarchive.omniverse;

import io.github.javaarchive.omniverse.world.EmptyWorldGenerator;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Lobby {
    Omniverse plugin;
    @Getter
    World lobbyWorld;
    public Lobby(Omniverse plugin){
        this.plugin = plugin;
        if(this.plugin.getServer().getWorld("lobby") == null){
            this.lobbyWorld = this.plugin.getServer().getWorld("world");
        }else if(this.plugin.config.contains("lobby_world")){
            this.lobbyWorld = this.plugin.getServer().getWorld(this.plugin.config.getString("lobby_world"));
        }else{
            this.lobbyWorld = this.plugin.getServer().getWorld("lobby");
        }
    }

    public void autocreateWorld(){
        if(Bukkit.getWorld("lobby") == null){
            this.plugin.getLogger().log(Level.INFO,"Hold tight, we're creating your lobby world");
            WorldCreator wc = new WorldCreator("lobby").type(WorldType.FLAT).generateStructures(false).generator(new EmptyWorldGenerator());
            this.lobbyWorld = wc.createWorld();
            this.plugin.getLogger().log(Level.INFO,"Lobby world creation completed");
        }
    }

    public Location getSpawn(){
        return this.lobbyWorld.getSpawnLocation();
    }
}
