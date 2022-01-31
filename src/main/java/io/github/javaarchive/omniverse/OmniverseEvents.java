package io.github.javaarchive.omniverse;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class OmniverseEvents implements Listener {
    Map<UUID, String> uuidToHost = new HashMap<>();
    Omniverse plugin;
    public OmniverseEvents(Omniverse plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        this.plugin.getLogger().log(Level.INFO, "Player " + event.getPlayer().getName() + " is logging in! ");
        if(this.plugin.config.contains("wildcard_suffix") && event.getHostname().endsWith(this.plugin.config.getString("wildcard_suffix"))){

        }
        uuidToHost.put(event.getPlayer().getUniqueId(),event.getHostname());
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.plugin.getLogger().log(Level.INFO, "Player " + event.getPlayer().getName() + " is joining! Redirecting to world if needed");
        if(this.plugin.config.contains("wildcard_suffix") && uuidToHost.get(event.getPlayer().getUniqueId()).endsWith(this.plugin.config.getString("wildcard_suffix"))){
            // TODO: redirect to apporiate server
            event.getPlayer().sendMessage("Redirecting you to the appropriate multiverse/universe. ");
        }else{
            // Lobby!
            if(this.plugin.getConfig().contains("lobby_welcome_message")) {
                event.getPlayer().sendMessage(this.plugin.config.getString("lobby_welcome_message"));
            }
            if(this.plugin.config.contains("should_auto_tp_to_lobby_on_join") && this.plugin.config.getBoolean("should_auto_tp_to_lobby_on_join")) {
                event.getPlayer().teleport(this.plugin.lobby.getSpawn());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.getLogger().log(Level.INFO, "Player " + event.getPlayer().getName() + " is quiting!");
        uuidToHost.remove(event.getPlayer().getUniqueId()); // memory leak prevention
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event){
        if(event.getWorld().getName().equals("world")){
            // this.plugin.getLobby().autocreateWorld();
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event){
        if(!event.getFrom().getWorld().getName().startsWith("world")){
            event.setCancelled(true);
        }
    }
}
