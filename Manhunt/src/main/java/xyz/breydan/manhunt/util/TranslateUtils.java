package xyz.breydan.manhunt.util;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TranslateUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String[] color(String... s) {
        return Arrays.stream(s).map(TranslateUtils::color).toArray(String[]::new);
    }

    public static List<String> color(Collection<String> collection) {
        return collection.stream().map(TranslateUtils::color).collect(Collectors.toList());
    }

    public static String formatTime(int secs) {
        int hours = secs / 3600;
        int secondsLeft = secs - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";

        if (hours > 0) {
            if (hours < 10) formattedTime += "0";
            formattedTime += hours + ":";
        }

        if (minutes < 10) formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10) formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }
}
