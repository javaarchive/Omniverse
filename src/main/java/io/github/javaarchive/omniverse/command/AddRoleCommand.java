package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.contexting.PlayerContext;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddRoleCommand implements CommandExecutor {
    Omniverse omniverse;

    public AddRoleCommand(Omniverse ov){
        this.omniverse = ov;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            return false;
        }

        boolean grantedPrivileges = false;

        Multiverse mv = null;
        if(args.length >= 2){
            // Get multiverse from last argument
            mv = this.omniverse.getMultiverse(args[args.length - 1]);
            if(mv == null){
                sender.sendMessage("Specified multiverse not found. ");
                return true;
            }
        }else{
            if(sender instanceof Player){
                Player player = (Player) sender;
                PlayerContext pctx = new PlayerContext(player);
                if(!pctx.isMultiversedWorld()){
                    player.sendRawMessage(ChatColor.RED + "You are not in a multiverse!" + ChatColor.RESET);
                }
                mv = pctx.mv;
                grantedPrivileges = mv.checkOwnership(player.getUniqueId());
            }else{
                // TODO: Configure trusting console
                grantedPrivileges = true; // We trust console right?
                sender.sendMessage("When running from non-player you must specify the multiverse as the first argument. ");
                return true;
            }
        }

        if(!grantedPrivileges){
            sender.sendMessage("Insufficient Privileges. ");
            return true;
        }

        // Set the permission
        //                   role
        mv.perms.appendRole(args[0]);
        sender.sendMessage("Role added! You now have " + mv.perms.getRoleList().size() + " roles!");
        return true;
    }
}
