package me.galtap.galtapcustomranks.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public final class SimpleUtil {
    private SimpleUtil(){}
    public static ConfigurationSection processSection(ConfigurationSection section, String name){
        if(section.contains(name)){
            return section.getConfigurationSection(name);
        }
        return section.createSection(name);
    }
    public static String getColorText(String text){
        if(text == null) return null;
        return ChatColor.translateAlternateColorCodes('&',text);
    }
}
