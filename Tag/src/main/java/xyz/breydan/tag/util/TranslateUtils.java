package xyz.breydan.tag.util;

import org.bukkit.ChatColor;

import java.util.Arrays;

public class TranslateUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String[] color(String... s) {
        return Arrays.stream(s).map(TranslateUtils::color).toArray(String[]::new);
    }
}
