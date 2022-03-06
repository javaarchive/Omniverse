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

public class DieCommand implements CommandExecutor {
    Omniverse omniverse;

    public DieCommand(Omniverse omniverse) {
        this.omniverse = omniverse;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            omniverse.getLogger().warning("Die command may be executed from the console (player found). ");
        }else{
            omniverse.onDisable();
            omniverse._debugNullifyDatabase();
            omniverse.getLogger().warning("Omniverse Database Killed itself via debug instruction command. ");
            return true;
        }
        return false;
    }
}
