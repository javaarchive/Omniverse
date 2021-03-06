package io.github.javaarchive.omniverse;

import io.github.javaarchive.omniverse.command.*;
import io.github.javaarchive.omniverse.database.Database;
import io.github.javaarchive.omniverse.database.DatabaseOptions;
import io.github.javaarchive.omniverse.database.LevelDatabase;
import io.github.javaarchive.omniverse.database.NamespacedDatabase;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.MultiverseUser;
import io.github.javaarchive.omniverse.structures.PermissionRegistry;
import io.github.javaarchive.omniverse.structures.Universe;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public final class Omniverse extends JavaPlugin implements CommandExecutor {
    OmniverseEvents eventListener;
    Database db;
    public Database universes;
    public Database multiverses;
    public Database players;
    public Database multiverseMembers;

    DatabaseOptions dbOpts;

    public FileConfiguration config; // public so not to be confused with the original getConfig

    public PermissionRegistry permsReg;

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

        this.permsReg = new PermissionRegistry();

        this.eventListener = new OmniverseEvents(this);
        getServer().getPluginManager().registerEvents(this.eventListener,this);

        this.lobby = new Lobby(this);

        // Register Commands

        CreateCommand create_cmd = new CreateCommand(this);
        this.getCommand("create").setExecutor(create_cmd);

        WarpCommand warp_cmd = new WarpCommand(this);
        this.getCommand("warp").setExecutor(warp_cmd);

        AddRoleCommand add_role_cmd = new AddRoleCommand(this);
        this.getCommand("add_role").setExecutor(add_role_cmd);
        // this.getCommand("addrole").setExecutor(add_role_cmd);

        DeleteRoleCommand delete_role_cmd = new DeleteRoleCommand(this);
        this.getCommand("delete_role").setExecutor(delete_role_cmd);

        SetPermCommand set_perm_cmd = new SetPermCommand(this);
        this.getCommand("set_perm").setExecutor(set_perm_cmd);
        // this.getCommand("setperm").setExecutor(set_perm_cmd);

        AssignRoleCommand assign_role_cmd = new AssignRoleCommand(this);
        this.getCommand("assign_role").setExecutor(assign_role_cmd);

        UnassignRoleCommand unassign_role_cmd = new UnassignRoleCommand(this);
        this.getCommand("unassign_role").setExecutor(unassign_role_cmd);

        ListRolesCommand list_roles_cmd = new ListRolesCommand(this);
        this.getCommand("list_roles").setExecutor(list_roles_cmd);
        
        AddProxyCommand add_proxy_cmd = new AddProxyCommand(this);
        this.getCommand("add").setExecutor(add_proxy_cmd);

        this.getCommand("debug").setExecutor(new DebugCommand(this));
        this.getCommand("debugger").setExecutor(new DebugCommand(this));

        this.getCommand("debug_die").setExecutor(new DieCommand(this));
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
        getLogger().info("Plugin shutdown finished. ");
    }

    public void _debugNullifyDatabase(){
        this.db = null;
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

    public World getWorld(String name){
        World world = this.getServer().getWorld(name);
        if(world == null){
            try{
                if(!(new File(name).isDirectory())){
                    return null;
                }
            }catch(Exception ex){
                return null;
            }
            world = this.getServer().createWorld(new WorldCreator(name));
            world.setKeepSpawnInMemory(false);
            return world;
        }else{
            world.setKeepSpawnInMemory(false);
            return world;
        }
    }

    private void onCentralDatabaseInit(){
        this.universes = this.quickNamespacedDatabase("universe");
        this.multiverses = this.quickNamespacedDatabase("multiverse");
        this.players = this.quickNamespacedDatabase("player");
        this.multiverseMembers = this.quickNamespacedDatabase("multiverse_playerdata");
    }

    public Universe getUniverse(String name){
        return this.universes.get_obj(name, Universe.class);
    }

    public Multiverse getMultiverse(String name){
        return this.multiverses.get_obj(name, Multiverse.class);
    }

    public void setUniverse(String name, Universe uv){
        this.universes.set_json_from_obj(name, uv);
    }

    public void setMultiverse(String name, Multiverse mv){
        this.multiverses.set_json_from_obj(name, mv);
    }

    @NonNull
    public Multiverse multiverseOf(Universe univ){
        return this.getMultiverse(univ.getParentMultiverseName());
    }

    public boolean hasMultiverse(String name){
        return this.multiverses.contains(name);
    }

    public boolean hasUniverse(String name){
        return this.universes.contains(name);
    }

    public boolean hasMultiverseUserProfile(String multiverseName, UUID puuid){
        return this.multiverseMembers.contains(multiverseName + ";" + puuid.toString());
    }

    public MultiverseUser getMultiverseUserProfile(String multiverseName, UUID puuid){
        if(!this.hasMultiverseUserProfile(multiverseName,puuid)){
            // Create if needed
            MultiverseUser mu = new MultiverseUser();
            MultiverseUser.initializeUser(mu);
            this.multiverseMembers.set_json_from_obj(multiverseName + ";" + puuid.toString(), mu);
        }
        return this.multiverseMembers.get_obj(multiverseName + ";" + puuid.toString(), MultiverseUser.class);
    }

    public void setMultiverseUserProfile(String multiverseName, UUID puuid, MultiverseUser mu){
        this.multiverseMembers.set_json_from_obj(multiverseName + ";" + puuid.toString(), mu);
    }

    public static Omniverse getInstance(){
        return (Omniverse) Arrays.stream(Bukkit.getPluginManager().getPlugins()).filter(plugin -> plugin instanceof Omniverse).findFirst().get();
    }
}
