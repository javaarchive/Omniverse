package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand implements CommandExecutor {
    Omniverse omniverse;

    public DebugCommand(Omniverse omniverse) {
        this.omniverse = omniverse;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            omniverse.getLogger().warning("Debug command may be executed from the console (player found). ");
        }else{
            omniverse.getLogger().warning("Executing Debug Instruction. ");
            if(args.length > 0){
                String action = args[0];
                if(action.equals("get_uv")){
                    omniverse.getLogger().info(args[1] + " = " + omniverse.universes.get(args[1]));
                }else if(action.equals("get_mv")){
                    omniverse.getLogger().info(args[1] + " = " + omniverse.multiverses.get(args[1]));
                }
            }else{
                omniverse.getLogger().warning("Debug command executed without args");
            }
            return true;
        }
        return false;
    }
}
