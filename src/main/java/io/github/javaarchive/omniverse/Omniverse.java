package io.github.javaarchive.omniverse;

import io.github.javaarchive.omniverse.command.CreateCommand;
import io.github.javaarchive.omniverse.database.Database;
import io.github.javaarchive.omniverse.database.DatabaseOptions;
import io.github.javaarchive.omniverse.database.LevelDatabase;
import io.github.javaarchive.omniverse.database.NamespacedDatabase;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Omniverse extends JavaPlugin implements CommandExecutor {
    OmniverseEvents eventListener;
    Database db;
    public Database universes;
    public Database multiverses;
    public Database players;

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
        this.onCentralDatabaseInit();


        this.eventListener = new OmniverseEvents(this);
        getServer().getPluginManager().registerEvents(this.eventListener,this);

        this.lobby = new Lobby(this);

        // Register Commands
        CreateCommand create_cmd = new CreateCommand((this));
        // getLogger().info("Got command " + this.getCommand("create"));
        this.getCommand("create").setExecutor(create_cmd);
        // Bukkit.getPluginCommand("create").setExecutor(new CreateCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
        if(this.db == null){
            getLogger().warning("Database never initialized correctly. ");
            return;
        }
        try {
            this.db.unlock(); // force!
        }catch(Exception ex){
            getLogger().warning("Failed to unlock database. ");
            ex.printStackTrace();
        }
        getLogger().info("Making DB Cleanup");
        try {
            this.db.cleanup();
        }catch(Exception ex){
            getLogger().warning("DB Failed to cleanup properly");
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // return super.onCommand(sender, command, label, args);\
        if(sender instanceof Player){
            // is player?
            Player player = (Player) sender;

        }
        return false;
    }

    private NamespacedDatabase quickNamespacedDatabase(String ns){
        DatabaseOptions dbOpts = new DatabaseOptions();
        dbOpts.setUpstreamDB(this.db);
        dbOpts.setNamespace(ns);
        return (new NamespacedDatabase(dbOpts));
    }

    private void onCentralDatabaseInit(){
        this.universes = this.quickNamespacedDatabase("universe");
        this.multiverses = this.quickNamespacedDatabase("multiverse");
        this.players = this.quickNamespacedDatabase("multiverse");
    }
}
