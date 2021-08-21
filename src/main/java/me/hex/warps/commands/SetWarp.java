package me.hex.warps.commands;

import me.hex.warps.Warps;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarp implements CommandExecutor {
    private final Warps plugin;

    public SetWarp(Warps plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("setwarp")){
            if(!(sender instanceof Player)){
                sender.sendMessage("Use your client.");
                return true;
            }
            Player player = (Player) sender;
            if(!player.hasPermission("warps.warps")) {
                sender.sendMessage(ChatColor.RED + "You do not have the permission to do this.");
                return true;
            }

            if(args.length != 1){
                sender.sendMessage(ChatColor.RED + "Use /setwarp {warpname}");
                return true;
            }
            Location location = player.getLocation();
            plugin.getConfig().createSection("warps." + args[0]);
            plugin.getConfig().set("warps." + args[0] + ".x", location.getX());
            plugin.getConfig().set("warps." + args[0] + ".y", location.getY());
            plugin.getConfig().set("warps." + args[0] + ".z", location.getZ());
            plugin.saveConfig();
            player.sendMessage(ChatColor.DARK_GREEN + "Created.");

        }
        return true;
    }
}
