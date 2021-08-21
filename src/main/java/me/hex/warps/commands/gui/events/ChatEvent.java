package me.hex.warps.commands.gui.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatEvent implements Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, Consumer<String>> waitingInputMap = new HashMap<>();

    public ChatEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void requestChatInput(final UUID playerID, final int timeout, final Consumer<String> consumer) {
        this.waitingInputMap.put(playerID, consumer);
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> this.timeOutMessage(playerID), timeout);
    }

    private void timeOutMessage(final UUID playerID) {
        final Consumer<String> consumer = this.waitingInputMap.remove(playerID);
        if (consumer != null) {
            consumer.accept(null);
        }
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final String message = event.getMessage();
        Optional.ofNullable(this.waitingInputMap.remove(event.getPlayer().getUniqueId())).ifPresent(consumer -> consumer.accept(message));
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.waitingInputMap.remove(event.getPlayer().getUniqueId());
    }

}