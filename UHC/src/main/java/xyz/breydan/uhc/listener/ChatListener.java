package xyz.breydan.uhc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static xyz.breydan.uhc.util.TranslateUtils.color;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String formatted = String.format(color("&a%1%s &8» &f%2%s"));
        event.setFormat(color(formatted));
    }
}
