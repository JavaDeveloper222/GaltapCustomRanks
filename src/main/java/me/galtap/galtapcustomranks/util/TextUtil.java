package me.galtap.galtapcustomranks.util;


import org.bukkit.ChatColor;

public final class TextUtil {
    private TextUtil(){}
    public static String getColorText(String text){
        if(text == null) return null;
        return ChatColor.translateAlternateColorCodes('&',text);
    }
}
