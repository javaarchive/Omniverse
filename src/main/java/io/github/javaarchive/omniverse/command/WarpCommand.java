package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.Utils;
import io.github.javaarchive.omniverse.procedures.UniverseWorld;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;
import io.github.javaarchive.omniverse.utils.Ratelimit;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
    Omniverse omniverse;
    Ratelimit rl;

    public WarpCommand(Omniverse omniverse) {
        this.omniverse = omniverse;
        this.rl = new Ratelimit(5 * 1000L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            // is player?
            Player player = (Player) sender;
            if(!rl.check(player.getUniqueId())){
                Utils.ratelimitReached(player);
                return true;
            }
            if(args.length == 1){
                // Create got to universe
                String name = args[0];

                if(this.omniverse.universes.contains(name)){
                    Universe uv = this.omniverse.getUniverse(name);
                    Multiverse mv = this.omniverse.multiverseOf(uv);
                    if(uv.checkOwnership(player.getUniqueId()) || mv.canJoin(player.getUniqueId())){
                        World w = omniverse.getWorld(name);
                        if(w == null){
                            player.sendRawMessage(ChatColor.RED + "ERROR: World System failed to locate associated world!" + ChatColor.RESET);
                            return true;
                        }
                        Utils.resetPlayer(player);
                        System.out.println("TPing to " +  w.getSpawnLocation());
                        player.teleport( w.getSpawnLocation()); // temporarily unsettable
                        player.setGameMode(GameMode.CREATIVE);
                        if(this.omniverse.config.getConfigurationSection("warp").getBoolean("sound")){
                            player.playSound(player.getLocation(),Sound.BLOCK_END_PORTAL_SPAWN,1,1);
                        }
                        player.sendRawMessage(ChatColor.GREEN + "Warped to the Universe " + name + "! " + ChatColor.RESET);
                    }else{
                        player.sendRawMessage(ChatColor.RED + "Not permitted to join!" + ChatColor.RESET);
                    }
                }else{
                    player.sendRawMessage(ChatColor.RED + "Universe not found!" + ChatColor.RESET);
                }

                return true;
            }else if(args.length == 0){
                return false;
            }
        }else{
            omniverse.getLogger().warning("Warp command may not be executed from the console (player not found). ");
            return true;
        }
        return false;
    }
}
