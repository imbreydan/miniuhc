package xyz.breydan.tag.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static xyz.breydan.tag.util.TranslateUtils.color;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String formatted = String.format(color("&b%1%s &7» &f%2%s"));
        event.setFormat(color(formatted).replace("$lenny$", "(ಠ ʖ ಠ)"));
    }
}
