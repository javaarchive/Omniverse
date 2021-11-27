package io.github.javaarchive.omniverse;

import org.iq80.leveldb.*;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Omniverse extends JavaPlugin {
    Options options;
    DB db;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        this.options = new Options();
        this.options.createIfMissing(true);
        try {
            this.db = factory.open(new File(this.getConfig().getString("db_path")),options);
        } catch (IOException e) {
            System.out.println("Could not init database");
            e.printStackTrace();
            System.exit(1); // bring the server down!
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
    }
}
