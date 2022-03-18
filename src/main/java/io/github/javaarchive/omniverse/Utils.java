package io.github.javaarchive.omniverse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import io.github.javaarchive.omniverse.structures.Permission;
import net.md_5.bungee.api.chat.TextComponent;

public class Utils {
    public static void resetPlayer(Player player){
        player.setLevel(0);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setSprinting(false);
        player.setGliding(false);
        player.setGlowing(false);
        player.resetTitle();
        player.resetPlayerTime();
        player.resetPlayerWeather();
        player.setSneaking(false);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
    }

    public static void ratelimitReached(Player player){
        player.sendRawMessage(ChatColor.RED + "You are rerunning this command too fast. Try again later." + ChatColor.RESET);
    }

    public Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void test(){
        // (Unused) Test Code and Autocomplete
    }

    public static TextComponent colorized(Permission perm){
        TextComponent tc = new TextComponent(perm.toString());
        switch(perm){
            case ALLOW:
                tc.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                break;
            case DENY:
                tc.setColor(net.md_5.bungee.api.ChatColor.RED);
                break;
            case NETURAL:
                tc.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
                break;
            default:
                tc.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        }
        return tc;
    }
}
