package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.Utils;
import io.github.javaarchive.omniverse.utils.Ratelimit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandExecutor {
    Omniverse omniverse;
    Ratelimit rl;

    public CreateCommand(Omniverse omniverse) {
        this.omniverse = omniverse;
        this.rl = new Ratelimit(2 * 1000L);
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
                // Create a multiverse
                if(this.omniverse.get)
            }else if(args.length == 2){

            }else if(args.length == 0){
                player.sendRawMessage(ChatColor.RED + "No argument given. Usage [multiverse name] or [universe name] [multiverse name]" + ChatColor.RESET);
            }
        }else{
            omniverse.getLogger().warning("Create command may not be executed from the console (player not found). ");
        }
        return false;
    }
}
