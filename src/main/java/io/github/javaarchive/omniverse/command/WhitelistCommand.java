package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.Utils;
import io.github.javaarchive.omniverse.contexting.PlayerContext;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhitelistCommand implements CommandExecutor {
    Omniverse omniverse;

    public WhitelistCommand(Omniverse omniverse) {
        this.omniverse = omniverse;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length == 0) return false;
            String action = args[0];

            Player player = (Player) sender;
            PlayerContext pctx = new PlayerContext(player);

            if(!pctx.isMultiversedWorld()){
                player.sendRawMessage(ChatColor.RED + "Not in a multiversed world" + ChatColor.RESET);
                return true;
            }

            Universe uv = pctx.uv;
            Multiverse mv = pctx.mv;

            if(action.equals("add")){
                Player target = player;
                if(args.length > 1 && Bukkit.getPlayer(args[1]) != null){
                    target = Bukkit.getPlayer(args[1]);
                }
                if(mv.getWhitelisted().contains(target.getUniqueId())){
                    player.sendRawMessage(ChatColor.RED + "Player is already whitelisted! " + ChatColor.RESET);
                    return true;
                }

                mv.getWhitelisted().add(target.getUniqueId());
                return true;
            }else if(action.equals("toggle")){
                mv.setUseWhitelist(!mv.isUseWhitelist());
                player.sendRawMessage(ChatColor.GREEN + "Whitelisted toggle to state: " + mv.isUseWhitelist() + ChatColor.RESET);
            }else if(action.equals("remove")){
                Player target = player;
                if(args.length > 1 && Bukkit.getPlayer(args[1]) != null){
                    target = Bukkit.getPlayer(args[1]);
                }
                if(!mv.getWhitelisted().contains(target.getUniqueId())){
                    player.sendRawMessage(ChatColor.RED + "Player is not even whitelisted! " + ChatColor.RESET);
                    return true;
                }

                mv.getWhitelisted().remove(target.getUniqueId());
            }

            this.omniverse.setUniverse(pctx.getUvName(), uv);
            this.omniverse.setMultiverse(pctx.uv.getParentMultiverseName(), mv);
        }else{
            omniverse.getLogger().warning("Whitelist command may not be executed from the console yet. ");
            return true;
        }
        return false;
    }
}
