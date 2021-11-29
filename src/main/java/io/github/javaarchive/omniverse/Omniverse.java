package io.github.javaarchive.omniverse;

import io.github.javaarchive.omniverse.database.Database;
import io.github.javaarchive.omniverse.database.DatabaseOptions;
import io.github.javaarchive.omniverse.database.LevelDatabase;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Omniverse extends JavaPlugin {
    OmniverseEvents eventListener;
    Database db;
    DatabaseOptions dbOpts;

    public FileConfiguration config; // public so not to be confused with the original getConfig

    @Getter
    Lobby lobby;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        this.config = this.getConfig();



        config.options().copyDefaults(true);
        saveConfig();


        this.dbOpts = new DatabaseOptions();
        this.dbOpts.setDbFile(new File(this.config.getString("db_path")));
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
