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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiMenu implements Listener {

    private final JavaPlugin plugin;
    private List<Inventory> pages;
    Pattern pattern = Pattern.compile("\\d+");
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

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {

        if (!next.isSimilar(event.getCurrentItem())) return;

        Matcher m = pattern.matcher(ChatColor.stripColor(event.getView().getTitle()));
        if (m.find()) {
            event.getWhoClicked().openInventory(pages.get(Integer.parseInt(m.group())));
        }
    }
}