package xyz.breydan.tag.game.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.command.GameCommand;
import xyz.breydan.tag.game.Game;
import xyz.breydan.tag.game.GameState;
import xyz.breydan.tag.profile.Profile;
import xyz.breydan.tag.profile.ProfileState;
import xyz.breydan.tag.settings.Settings;
import java.util.Arrays;

import static xyz.breydan.tag.util.TranslateUtils.color;

public class GameTask implements Runnable {

    private World world = Bukkit.getWorlds().get(0);

    public GameTask() {
        for (Profile profile : Tag.getInstance().getProfileManager().getProfiles().values()) {
            profile.setProfileState(ProfileState.GAME);
        }
        world.getWorldBorder().setCenter(0, 0);
        world.getWorldBorder().setSize(20_000 * 2);
    }

    @Override
    public void run() {
        Game game = Tag.getInstance().getGameManager().getGame();
        int duration = game.increaseDuration();
        if (game.decreaseBorderTime() == 0) { // Run code at next border cycle
            if (game.getBorder() == 500) {
                if (game.allRunnersAlive()) {
                    Bukkit.broadcastMessage(color("&cNo runners have died! Runners win!"));
                    Bukkit.getWorlds().get(0).getWorldBorder().setSize(1000 * 2);
                    game.setGameState(GameState.FINISHED);

                    return;
                }
            } else {
                game.setBorderTime(60 * Settings.BORDER_CHANGE_INTERVAL_MINS);
            }
            world.getWorldBorder().setSize(game.getNextBorder() * 2);
            game.setBorder(game.getNextBorder());
            Bukkit.broadcastMessage(color("&6The border has been shrunk to &e" + game.getBorder() + "&6."));
            Bukkit.getWorlds().get(0).getEntities().stream().filter(entity -> entity.getType() == EntityType.DROPPED_ITEM).forEach(Entity::remove);
        } else if (Arrays.asList(300, 240, 180, 120, 60, 30, 15, 10, 5, 4, 3, 2, 1).contains(game.getBorderTime())) {
            if (game.getBorderTime() < 60) {
                Bukkit.broadcastMessage(color("&6The border will shrink to &e" + game.getNextBorder() + " &6in &e" + game.getBorderTime() / 60 + " &6minute(s)."));
            } else {
                Bukkit.broadcastMessage(color("&6The border will shrink to &e" + game.getNextBorder() + " &6in &e" + game.getBorderTime() + " &6second(s)."));
            }
        }
    }
}
