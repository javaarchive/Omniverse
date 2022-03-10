package io.github.javaarchive.omniverse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

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
}
