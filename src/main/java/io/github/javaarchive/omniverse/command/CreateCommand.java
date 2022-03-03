package io.github.javaarchive.omniverse.command;

import io.github.javaarchive.omniverse.Omniverse;
import io.github.javaarchive.omniverse.Utils;
import io.github.javaarchive.omniverse.generation.DefaultUniverseSetup;
import io.github.javaarchive.omniverse.procedures.UniverseWorld;
import io.github.javaarchive.omniverse.structures.Multiverse;
import io.github.javaarchive.omniverse.structures.Universe;
import io.github.javaarchive.omniverse.utils.Ratelimit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandExecutor {
    Omniverse omniverse;
    Ratelimit rl;

    public CreateCommand(Omniverse omniverse) {
        this.omniverse = omniverse;
        this.rl = new Ratelimit(2 * 1000L);
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
            if(args.length == 1){
                // Create a new multiverse
                String name = args[0];
                if(!this.omniverse.hasMultiverse(name)){
                    // Create!
                    Multiverse mv = new Multiverse();
                    mv.setOwner(player.getUniqueId());
                    this.omniverse.multiverses.set_json_from_obj(name, mv);
                    player.sendRawMessage(ChatColor.GREEN + "Congratulations, you just created your first multiverse. Next, add universe to it with /create_universe <universe_name> " + name + ChatColor.RESET);
                }else{
                    player.sendRawMessage(ChatColor.RED + "A multiverse with that name already exists" + ChatColor.RESET);
                }
                return true;
            }else if(args.length == 2){
                // Create universe part of multiverse
                String universeName = args[0];
                String multiverseName = args[1];
                if(this.omniverse.universes.contains(universeName)){
                    player.sendRawMessage(ChatColor.RED + "Universe with name already exists. Try again!" + ChatColor.RESET);
                    return true;
                }else if(!this.omniverse.hasMultiverse(multiverseName)){
                    player.sendRawMessage(ChatColor.RED + "Multiverse not found!" + ChatColor.RESET);
                    return true;
                }
                Multiverse mv = this.omniverse.getMultiverse(multiverseName);
                if(!mv.checkOwnership(player.getUniqueId())){
                    player.sendRawMessage(ChatColor.RED + "No permission to create a new universe attached to this multiverse!" + ChatColor.RESET);
                    return true;
                }
                
                Universe uv = new Universe();
                uv.setOwner(player.getUniqueId());
                uv.setParentMultiverseName(multiverseName);

                mv.universes.add(universeName);

                this.omniverse.universes.set_json_from_obj(universeName, uv);
                this.omniverse.multiverses.set_json_from_obj(multiverseName, mv);

                player.sendRawMessage(ChatColor.YELLOW + "Generating Initial Files" + ChatColor.RESET);

                UniverseWorld.generateNew(universeName,new String[0]);

                player.sendRawMessage(ChatColor.GREEN + "Universe created! To tp to it /warp " + universeName);

                return true;
            }else if(args.length == 0){
                // player.sendRawMessage(ChatColor.RED + "No argument given. Usage [multiverse name] or [universe name] [multiverse name]" + ChatColor.RESET);
                return false;
            }
        }else{
            omniverse.getLogger().warning("Create command may not be executed from the console (player not found). ");
            return true;
        }
        return false;
    }
}
