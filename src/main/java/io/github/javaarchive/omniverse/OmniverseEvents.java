package io.github.javaarchive.omniverse;

import io.github.javaarchive.omniverse.contexting.PlayerContext;
import io.github.javaarchive.omniverse.structures.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        this.plugin.permsReg.addPerm("player.drop_item");
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
            if(this.plugin.getConfig().contains("should_auto_tp_to_lobby_on_join") && this.plugin.config.getBoolean("should_auto_tp_to_lobby_on_join")) {
                event.getPlayer().teleport(this.plugin.lobby.getSpawn());
            }
        }

        PlayerContext pctx = new PlayerContext(event.getPlayer());
        if(pctx.isMultiversedWorld()){
            if(pctx.mv.isTutorialMode()){
                // Send tutorial message
                pctx.player.sendMessage(this.plugin.getConfig().getString("tutorial_message"));
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
            if(event.getPlayer() != null){
                event.getPlayer().sendRawMessage(ChatColor.DARK_RED + "Portals not implemented " + ChatColor.RESET);
            }else{
                event.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        PlayerContext pctx = new PlayerContext(event.getPlayer());
        if(!pctx.isMultiversedWorld()){
            return;
        }
        if(pctx.checkPerm("player.drop_item") != Permission.ALLOW){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        PlayerContext pctx = new PlayerContext(event.getPlayer());
        if(!pctx.isMultiversedWorld()){
            return;
        }
        if(pctx.checkPerm("player.move") == Permission.DENY){
            event.setCancelled(true);
        }
    }
}
