package me.hex.warps.commands.gui;

import me.hex.warps.Warps;
import me.hex.warps.commands.gui.utils.GuiMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GuiWarps implements CommandExecutor {
    private final Warps plugin;
    private final GuiMenu menu;

    public GuiWarps(Warps plugin, GuiMenu menu) {
        this.plugin = plugin;
        this.menu = menu;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("warps")){
            if(!(sender instanceof Player)){
                sender.sendMessage("Use your client.");
                return true;
            }
            Player player = (Player) sender;
            if(!player.hasPermission("warps.warps")) {
                player.sendMessage(ChatColor.RED + "You do not have the permission to do this.");
                return true;
            }
            menu.openMenu(player);

            return true;
        }
        return true;
    }
}
