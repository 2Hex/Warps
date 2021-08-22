package me.hex.warps.commands.gui.utils;

import me.hex.warps.Warps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiMenu {
    public Inventory getInventory(){
        Inventory gui2 = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Warps: (Page 2)");
        return gui2;
    }
    public static ItemStack next(){
        ItemStack next = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName(ChatColor.DARK_PURPLE + "Next Page");
        next.setItemMeta(nextMeta);
        return next;
    }
    private final Warps plugin;

    public GuiMenu(Warps plugin) {
        this.plugin = plugin;
    }

    public void openMenu(Player player) {
        if (plugin.getConfig().getConfigurationSection("warps") == null) {
            player.sendMessage(ChatColor.RED + "There are no warps.");
            return;
        }
        if (!plugin.getConfig().isSet("warps")) {
            player.sendMessage(ChatColor.RED + "There are no warps.");
            return;
        }
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("warps");

        Inventory gui = Bukkit.createInventory(player, 54, ChatColor.GREEN + "Warps:");
        getInventory().setItem(53, next());
        //For every player, add their name to gui
        int i = 0;
        if (section.getKeys(false).isEmpty()) {
            player.sendMessage(ChatColor.RED + "There are no warps.");
            return;
        }
        if (section.getKeys(false) == null) {
            player.sendMessage(ChatColor.RED + "There are no warps.");
            return;
        }

        for (String childSection : section.getKeys(false)) {
            if (i == 54) {
                i++;
                continue;
            }
            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + childSection);
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "Warp to: " + ChatColor.RED + childSection);
            meta.setLore(lore);
            item.setItemMeta(meta);
            if (i < 54) {
                gui.addItem(item);
                if(gui.getItem(53).getType() != Material.ARROW)
                    gui.setItem(53, next());
            }
            i++;
            getInventory().addItem(item);

        }
        player.openInventory(gui);

    }

}


