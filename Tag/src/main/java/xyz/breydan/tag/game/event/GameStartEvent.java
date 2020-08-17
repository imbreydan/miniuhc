package xyz.breydan.tag.game.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;
import java.util.UUID;

public class GameStartEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final List<UUID> players, runners;

    public GameStartEvent(List<UUID> players, List<UUID> runners) {
        this.players = players;
        this.runners = runners;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public List<UUID> getRunners() {
        return runners;
    }
}
