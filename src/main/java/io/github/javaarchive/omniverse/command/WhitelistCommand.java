package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.Utils;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;
import io.github.javaarchive.omniverse.utils.Ratelimit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
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
        }else{
            omniverse.getLogger().warning("Warp command may not be executed from the console yet. ");
            return true;
        }
        return false;
    }
}
