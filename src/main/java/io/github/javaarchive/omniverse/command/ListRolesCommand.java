package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.contexting.PlayerContext;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.MultiverseUser;
import io.github.javaarchive.omniverse.structures.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ListRolesCommand implements CommandExecutor {
    Omniverse omniverse;

    public ListRolesCommand(Omniverse ov){
        this.omniverse = ov;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Multiverse mv = null;
        boolean isConsole = false;

        if(!(sender instanceof Player)){
            sender.sendMessage("Console role listing not supported yet. ");
            isConsole = true;
            return true;
        }

        Player player = isConsole?null:((Player) sender);
        
        if(isConsole){
            mv = this.omniverse.getMultiverse(args[0]);
        }else{
            PlayerContext pctx = new PlayerContext(player);

            if(!pctx.isMultiversedWorld()){
                player.sendRawMessage(ChatColor.RED + "You are not in a multiverse!" + ChatColor.RESET);
                return true;
            }

            mv = pctx.mv;
        }

        Player targetPlayer = (Player) sender;

        if(args.length > 1){
            Optional<? extends Player> possiblePlayer = Bukkit.getOnlinePlayers().stream().filter(p -> p.getName().equals(args[args.length - 1])).findFirst();
            if(!possiblePlayer.isPresent()){
                sender.sendMessage("Player is not online. We support adding roles to online players only at the moment. ");
                return true;
            }
            targetPlayer = possiblePlayer.get();
        }

        PlayerContext pctx = new PlayerContext(targetPlayer);

        MultiverseUser mu = pctx.getMemberData();

        String roleStr = String.join(",",pctx.mv.perms.sortRoles(mu.getRoles()));
        String allRolesStr = String.join(",",pctx.mv.perms.getRoleList());
        
        sender.sendMessage("Your/Player's roles: " + roleStr );
        sender.sendMessage("Roles in Multiverse: " + allRolesStr );
        
        return true;
    }
}
