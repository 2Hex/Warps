package me.hex.warps.commands.gui.events;

import me.hex.warps.Warps;
import me.hex.warps.commands.gui.utils.RenameConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickEvent implements Listener {
    private final Warps plugin;
    private final ChatEvent chat;

    public ClickEvent(Warps plugin, ChatEvent chat) {
        this.plugin = plugin;
        this.chat = chat;
    }
    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory() == null || event.getClickedInventory().getTitle() == null) return;
        if (!event.getClickedInventory().getTitle().contains(ChatColor.GREEN + "Warps")) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        if (event.getCurrentItem().getType() == Material.PAPER) {
            System.out.println("4");
            if (event.isShiftClick()) {
                System.out.println("shift");
                ItemStack currentItem = event.getCurrentItem();
                ItemMeta currentMeta = currentItem.getItemMeta();
                String shiftWarp = ChatColor.stripColor(currentMeta.getDisplayName());
                player.sendMessage(ChatColor.GREEN + "Send Rename in chat");
                event.setCancelled(true);
                player.closeInventory();

                if (!plugin.getConfig().isConfigurationSection("warps." + shiftWarp)) {
                    player.sendMessage(ChatColor.RED + "This warp does not exist / is not a warp.");
                    System.out.println("no exist");
                    return;
                }
                chat.requestChatInput(player.getUniqueId(), 5000, input -> {
                    player.sendMessage("§eRenamed to: §f" + input);
                    FileConfiguration config = plugin.getConfig();
                    if (input == null) {
                        player.sendMessage(ChatColor.RED + "Timed out");
                        return;
                    }
                    RenameConfig.copyConfigSection(config, "warps." + shiftWarp, "warps." + input);
                    currentMeta.setDisplayName(ChatColor.YELLOW + input);
                });
                return;
            }
            if (event.isLeftClick()) {
                System.out.println("left");
                ItemMeta meta = event.getCurrentItem().getItemMeta();
                String newWarp = ChatColor.stripColor(meta.getDisplayName());
                player.chat("/warp " + newWarp);
                event.setCancelled(true);
                player.closeInventory();
                return;
            }
            if (event.getClick().equals(ClickType.DROP)) {
                System.out.println("drop");
                ItemMeta meta = event.getCurrentItem().getItemMeta();
                String newerWarp = ChatColor.stripColor(meta.getDisplayName());
                event.getClickedInventory().remove(event.getCurrentItem());
                player.chat("/deletewarp " + newerWarp);
                event.setCancelled(true);
                player.closeInventory();
            }
        }
    }
    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if(!event.getInventory().getTitle().contains(ChatColor.GREEN + "Warps")) return;
        event.setCancelled(true);
    }
}
