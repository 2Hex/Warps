package me.hex.warps.commands;

import me.hex.warps.Warps;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteWarp implements CommandExecutor {
    private final Warps plugin;

    public DeleteWarp(Warps plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("deletewarp")){

            Player player = (Player) sender;
            if(!player.hasPermission("warps.warps")) {
                player.sendMessage(ChatColor.RED + "You do not have the permission to do this.");
                return true;
            }

            if(args.length != 1){
                player.sendMessage(ChatColor.RED + "Use /deletewarp {warpname}");
                return true;
            }
            if(!plugin.getConfig().isConfigurationSection("warps." + args[0])){
                player.sendMessage(ChatColor.RED + "There is no Warp such as " + ChatColor.YELLOW + args[0]);

            }
            plugin.getConfig().set("warps." + args[0], null);
            player.sendMessage(ChatColor.GREEN + "Deleted Successfully.");
            return true;
        }
        return true;
    }
}
