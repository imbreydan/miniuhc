package xyz.breydan.tag.game;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.breydan.tag.game.event.GameStartEvent;

import java.util.*;

public class Game {

    private final String gameId;
    private final List<UUID> players;
    private List<UUID> runners = new ArrayList<>();
    private UUID winner;

    public Game(List<UUID> players) {
        this.gameId = new Random().nextInt(8) + "GID";
        this.players = players;

        startGame();
    }

    private void startGame() {
        Bukkit.getPluginManager().callEvent(new GameStartEvent(players, runners));
    }

    private void end(boolean runnersWon) {
        
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public List<UUID> getRunners() {
        return runners;
    }

    public void setRunners(List<UUID> runners) {
        this.runners = runners;
    }

    public void addRunner(UUID uuid) {
        runners.add(uuid);
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(gameId, game.gameId) &&
                Objects.equals(players, game.players) &&
                Objects.equals(runners, game.runners) &&
                Objects.equals(winner, game.winner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, players, runners, winner);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", players=" + players +
                ", runners=" + runners +
                ", winner=" + winner +
                '}';
    }
}
