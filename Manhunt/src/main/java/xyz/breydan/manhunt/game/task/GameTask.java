package xyz.breydan.manhunt.game.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import xyz.breydan.manhunt.Manhunt;
import xyz.breydan.manhunt.game.Game;
import xyz.breydan.manhunt.settings.Settings;

import java.util.Arrays;

import static xyz.breydan.manhunt.util.TranslateUtils.color;

public class GameTask implements Runnable {

    private World world = Bukkit.getWorlds().get(0);

    public GameTask() {
        world.getWorldBorder().setCenter(0, 0);
        world.getWorldBorder().setSize(20_000 * 2);
    }

    @Override
    public void run() {
        Game game = Manhunt.getInstance().getGameManager().getGame();
        int duration = game.increaseDuration();
        if (game.decreaseBorderTime() == 0) {
            game.setBorderTime(60 * Settings.BORDER_CHANGE_INTERVAL_MINS);
            world.getWorldBorder().setSize(game.getNextBorder() * 2);
            game.setBorder(game.getNextBorder());
            Bukkit.broadcastMessage(color("&6The border has been shrunk to &e" + game.getBorder() + "&6."));
        } else if (Arrays.asList(300, 240, 180, 120, 60, 30, 15, 10, 5, 4, 3, 2, 1).contains(game.getBorderTime())) {
            if (game.getBorderTime() >= 60) {
                Bukkit.broadcastMessage(color("&6The border will shrink to &e" + game.getNextBorder() + " &6in &e" + game.getBorderTime() / 60 + " &6minute(s)."));
            } else {
                Bukkit.broadcastMessage(color("&6The border will shrink to &e" + game.getNextBorder() + " &6in &e" + game.getBorderTime() + " &6second(s)."));
            }
        }
    }
}
