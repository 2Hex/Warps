package me.hex.warps.commands.gui.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RenameConfig {
    public static void copyConfigSection(FileConfiguration config, String fromPath, String toPath){
        Map<String, Object> vals = config.getConfigurationSection(fromPath).getValues(true);
        String toDot = toPath.equals("") ? "" : ".";
        for (String s : vals.keySet()){
            System.out.println(s);
            Object val = vals.get(s);
            if (val instanceof List)
                val = new ArrayList((List)val);
            config.set(toPath + toDot + s, val);
        }
        config.set(fromPath, null);
    }
}
