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

public class SetPermCommand implements CommandExecutor {
    Omniverse omniverse;

    public SetPermCommand(Omniverse ov){
        this.omniverse = ov;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 3){
            return false;
        }

        boolean grantedPrivileges = false;

        Multiverse mv = null;
        String mvName = "";
        if(args.length >= 4){
            mv = this.omniverse.getMultiverse(args[0]);
            mvName = args[0];
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
                mvName = pctx.uv.getParentMultiverseName();
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

        if(!mv.perms.hasRole(args[args.length - 3])){
            sender.sendMessage("Role not found. ");
            return true;
        }

        // Set the permission
        //                role                   perms
        mv.perms.setPerm(args[args.length - 3],args[args.length - 2], Permission.fromString(args[args.length - 1]));

        this.omniverse.setMultiverse(mvName,mv); // Save!

        sender.sendMessage("Permission Set/Updated");

        return true;
    }
}
