package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.Utils;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;
import io.github.javaarchive.omniverse.utils.Ratelimit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempCommand implements CommandExecutor {
    Omniverse omniverse;
    Ratelimit rl;

    public TempCommand(Omniverse omniverse) {
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

        }else{
            omniverse.getLogger().warning("Command may not be executed from the console (player not found). ");
            return true;
        }
        return false;
    }
}
