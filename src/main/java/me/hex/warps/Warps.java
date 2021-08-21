package me.hex.warps;

import me.hex.warps.utils.Registration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Warps extends JavaPlugin {

    @Override
    public void onEnable() {
        Registration.registry(this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
