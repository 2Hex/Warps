package me.hex.warps.utils;

import me.hex.warps.Warps;
import me.hex.warps.commands.DeleteWarp;
import me.hex.warps.commands.SetWarp;
import me.hex.warps.commands.Warp;
import me.hex.warps.commands.gui.GuiWarps;
import me.hex.warps.commands.gui.events.ChatEvent;
import me.hex.warps.commands.gui.events.ClickEvent;
import me.hex.warps.commands.gui.utils.GuiMenu;
import org.bukkit.plugin.PluginManager;

public interface Registration {
    static void registry(final Warps plugin) {
        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        ChatEvent chatEvent = new ChatEvent(plugin);
        GuiMenu menu = new GuiMenu(plugin);
        pluginManager.registerEvents(chatEvent, plugin);
        pluginManager.registerEvents(new ClickEvent(plugin, chatEvent), plugin);
        pluginManager.registerEvents(menu, plugin);
        plugin.getCommand("warp").setExecutor(new Warp(plugin));
        plugin.getCommand("deletewarp").setExecutor(new DeleteWarp(plugin));
        plugin.getCommand("setwarp").setExecutor(new SetWarp(plugin));
        plugin.getCommand("warps").setExecutor(new GuiWarps(plugin, menu));

    }
}


