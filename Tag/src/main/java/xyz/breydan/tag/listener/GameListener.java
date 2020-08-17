package xyz.breydan.tag.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.breydan.tag.game.event.GameStartEvent;

import java.util.Objects;

import static xyz.breydan.tag.util.TranslateUtils.color;

public class GameListener implements Listener {

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        event.getPlayers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(player -> {
            player.sendMessage(color("&6Game started. You're a hunter."));
        });
        event.getRunners().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(runner -> {
            runner.sendMessage(color("&6Game started. You're a runner."));
        });
    }

}
