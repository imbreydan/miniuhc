package xyz.breydan.tag.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.profile.ProfileState;
import xyz.breydan.tag.settings.Settings;
import xyz.breydan.tag.util.ScatterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static xyz.breydan.tag.util.TranslateUtils.formatTime;

public class Game {

    private GameState gameState = GameState.LOBBY;

    private List<UUID> runners = new ArrayList<>(), hunters = new ArrayList<>();

    private int duration;
    private int borderTime = 60 * Settings.BORDER_CHANGE_INTERVAL_MINS;
    private int border = 20_000;

    public int getNextBorder() {
        switch (border) {
            case 20_000:
                return 10_000;
            case 10_000:
                return 2_500;
            default:
                return 2_500;
        }
    }

    public String getGameStatus() {
        switch (gameState) {
            case PLAYING:
                return "Playing";
            case SCATTERING:
                return "Scattering";
            default:
                return "Waiting";
        }
    }

    public int increaseDuration() {
        return ++duration;
    }

    public int decreaseBorderTime() {
        return --borderTime;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
        ScatterUtil.scatter(runners, true, false);
        ScatterUtil.scatter(hunters, false, false);
    }

    public String getFormattedDuration() {
        return formatTime(duration);
    }

    public String getFormattedBorderTime() {
        return formatTime(borderTime);
    }

    public int getDuration() {
        return duration;
    }

    public int getBorderTime() {
        return borderTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setBorderTime(int borderTime) {
        this.borderTime = borderTime;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<UUID> getAllPlayers() {
        List<UUID> uuids = new ArrayList<>();
        uuids.addAll(hunters);
        uuids.addAll(runners);
        return uuids;
    }

    public List<UUID> getRunners() {
        return runners;
    }

    public List<UUID> getHunters() {
        return hunters;
    }

    public void addRunner(Player player) {
        hunters.remove(player.getUniqueId());
        runners.add(player.getUniqueId());
    }

    public void addHunter(Player player) {
        runners.remove(player.getUniqueId());
        hunters.add(player.getUniqueId());
    }

    public boolean allRunnersAlive() {
        AtomicInteger alive = new AtomicInteger();
        runners.stream().map(uuid -> Tag.getInstance().getProfileManager().getProfile(uuid)).filter(profile -> profile.getProfileState() == ProfileState.GAME).forEach(player -> {
            alive.incrementAndGet();
        });
        return alive.get() == runners.size();
    }

    public List<UUID> getTeam(UUID uuid) {
        if (runners.contains(uuid))
            return runners;
        else
            return hunters;
    }

}
