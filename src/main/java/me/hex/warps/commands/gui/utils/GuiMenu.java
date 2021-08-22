package me.hex.warps.commands.gui.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GuiMenu implements Listener {

    private final JavaPlugin plugin;
    private List<Inventory> pages;
    private static final ItemStack next = new ItemStack(Material.ARROW);

    static {
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName(ChatColor.DARK_PURPLE + "Next Page");
        next.setItemMeta(nextMeta);
    }

    public GuiMenu(JavaPlugin plugin) {

        this.plugin = plugin;
    }

    public void openMenu(Player player) {

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("warps");

        if (section == null) {
            player.sendMessage(ChatColor.RED + "There are no warps.");
            return;
        }

        pages = new ArrayList<>();
        Inventory currentPage = newPage();
        int i = 0;

        for (String childSection : section.getKeys(false)) {

            if (i == 53) {
                i = 0;
                currentPage.setItem(53, next);
                currentPage = newPage();
            }

            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + childSection);
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "Warp to: " + ChatColor.RED + childSection);
            meta.setLore(lore);
            item.setItemMeta(meta);

            currentPage.addItem(item);
            i++;
        }

        if (!pages.isEmpty()) player.openInventory(pages.get(0));
    }

    private Inventory newPage() {

        Inventory inven = Bukkit.createInventory(null, 54, ChatColor.GREEN + String.format("Warps: (Page %d)", pages.size() + 1));
        pages.add(inven);

        return pages.get(pages.size() - 1);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!pages.contains(event.getClickedInventory())) return;
        event.setCancelled(true);

        if (!next.isSimilar(event.getCurrentItem())) return;

        int index = pages.indexOf(event.getClickedInventory());

        // Make sure we have a new menu to display.
        if (pages.size() > index) {
            event.getWhoClicked().openInventory(pages.get(index + 1));
        }
    }
}