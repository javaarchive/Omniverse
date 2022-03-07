package io.github.javaarchive.omniverse.command;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.javaarchive.omniverse.Omniverse;

public class AddProxyCommand implements CommandExecutor{

    Omniverse ov;

    AddRoleCommand add_role_cmd;

    public AddProxyCommand(Omniverse ov){
        this.ov = ov;
        this.add_role_cmd = new AddRoleCommand(this.ov);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0){
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            if(args[0].equals("role")){
                // /add role
                return this.add_role_cmd.onCommand(sender,this.ov.getCommand("add_role"),"add_role", subArgs);
            }
        }
        return false;
    }
    
}
