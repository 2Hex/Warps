package me.hex.warps.commands;

import me.hex.warps.Warps;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp implements CommandExecutor {
    private final Warps plugin;

    public Warp(Warps plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("warp")){
            if(!(sender instanceof Player)){
                sender.sendMessage("Use your client.");
                return true;
            }
            Player player = (Player) sender;
            if(!player.hasPermission("warps.warps")) {
                player.sendMessage(ChatColor.RED + "You do not have the permission to do this.");
                return true;
            }

            if(args.length != 1){
                player.sendMessage(ChatColor.RED + "Use /warp {warpname}");
                return true;
            }
            if(!plugin.getConfig().isConfigurationSection("warps." + args[0])){
                player.sendMessage(ChatColor.RED + "There is no Warp such as " + ChatColor.YELLOW + args[0]);
                return true;
            }
            Location loc = new Location(player.getWorld(), plugin.getConfig().getDouble("warps." + args[0] + ".x"), plugin.getConfig().getDouble("warps." + args[0] + ".y"), plugin.getConfig().getDouble("warps." + args[0] + ".z"));
            player.teleport(loc);
            player.sendMessage(ChatColor.GREEN + "Teleported Successfully.");
            return true;
        }
        return true;
    }
}