package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.Utils;
import io.github.javaarchive.omniverse.procedures.UniverseWorld;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;
import io.github.javaarchive.omniverse.utils.Ratelimit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
                    Universe uv = this.omniverse.universes.get_obj(name, Universe.class);
                    if(uv.checkOwnership(player.getUniqueId())){
                        World w = Bukkit.getWorld(name);
                        Utils.resetPlayer(player);
                        player.teleport( w.getSpawnLocation()); // temporarily unsettable

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
