package io.github.javaarchive.omniverse;

import io.github.javaarchive.omniverse.database.Database;
import io.github.javaarchive.omniverse.database.DatabaseOptions;
import io.github.javaarchive.omniverse.database.LevelDatabase;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Omniverse extends JavaPlugin {
    OmniverseEvents eventListener;
    Database db;
    DatabaseOptions dbOpts;

    @Getter
    Lobby lobby;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        this.dbOpts = new DatabaseOptions();
        this.dbOpts.setDbFile(new File(this.getConfig().getString("db_path")));
        this.db = new LevelDatabase(this.dbOpts);

        this.eventListener = new OmniverseEvents(this);

        this.lobby = new Lobby(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
        this.db.unlock(); // force!
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // return super.onCommand(sender, command, label, args);
        return false;
    }
}
