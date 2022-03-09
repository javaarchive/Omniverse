package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.contexting.PlayerContext;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.MultiverseUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class UnassignRoleCommand implements CommandExecutor {
    Omniverse omniverse;

    public UnassignRoleCommand(Omniverse ov){
        this.omniverse = ov;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            return false;
        }

        boolean grantedPrivileges = false;

        Multiverse mv = null;

        if(!(sender instanceof Player)){
            sender.sendMessage("Console role giving not supported yet. ");
            return true;
        }

        Player player = (Player) sender;
        PlayerContext pctx = new PlayerContext(player);
        if(!pctx.isMultiversedWorld()){
            player.sendRawMessage(ChatColor.RED + "You are not in a multiverse!" + ChatColor.RESET);
        }
        mv = pctx.mv;
        grantedPrivileges = mv.checkOwnership(player.getUniqueId());

        if(!grantedPrivileges){
            sender.sendMessage("Insufficient Privileges. ");
            return true;
        }

        Player targetPlayer = (Player) sender;

        if(args.length > 1){
            Optional<? extends Player> possiblePlayer = Bukkit.getOnlinePlayers().stream().filter(p -> p.getName().equals(args[0])).findFirst();
            if(!possiblePlayer.isPresent()){
                sender.sendMessage("Player is not online. We support removing roles to online players only at the moment. ");
                return true;
            }
            targetPlayer = possiblePlayer.get();
        }

        MultiverseUser mu = pctx.getMemberData();

        if(!mu.getRoles().contains(args[args.length - 1])){
            player.sendRawMessage(ChatColor.RED + "Player doesn't have role! " + ChatColor.RESET);
            return true;
        }

        mu.getRoles().remove(args[args.length - 1]);

        pctx.setMemberData(mu);

        sender.sendMessage("Role removed! You/they now have " + mu.getRoles().size() + " roles!");

        return true;
    }
}
