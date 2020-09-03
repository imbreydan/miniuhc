package xyz.breydan.uhc.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.breydan.uhc.settings.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static xyz.breydan.uhc.util.TranslateUtils.color;
import static xyz.breydan.uhc.util.TranslateUtils.formatTime;

public class Game {

    private List<UUID> betas = new ArrayList<>(), alphas = new ArrayList<>();

    private Inventory alphaBackpack = Bukkit.createInventory(null, 27, color("Backpack #1")), betaBackpack = Bukkit.createInventory(null, 27, color("Backpack #2"));

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

    public List<UUID> getAllPlayers() {
        List<UUID> uuids = new ArrayList<>();
        uuids.addAll(alphas);
        uuids.addAll(betas);
        return uuids;
    }

    public List<UUID> getBetas() {
        return betas;
    }

    public List<UUID> getAlphas() {
        return alphas;
    }

    public void addAlpha(Player player) {
        alphas.remove(player.getUniqueId());
        betas.add(player.getUniqueId());
    }

    public void addBeta(Player player) {
        betas.remove(player.getUniqueId());
        alphas.add(player.getUniqueId());
    }

    public Inventory getAlphaBackpack() {
        return alphaBackpack;
    }

    public Inventory getBetaBackpack() {
        return betaBackpack;
    }

    public List<UUID> getTeam(UUID uuid) {
        if (betas.contains(uuid))
            return betas;
        else
            return alphas;
    }

}
