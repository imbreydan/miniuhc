package xyz.breydan.uhc.game.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import xyz.breydan.uhc.UHC;
import xyz.breydan.uhc.game.Game;

import static xyz.breydan.uhc.util.TranslateUtils.color;

public class GameTask extends Thread {

    private World world = Bukkit.getWorlds().get(0);
    private Game game = UHC.getInstance().getGame();

    @Override
    public void run() {
        game.increaseDuration();

    }
}
