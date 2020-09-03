package xyz.breydan.uhc.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;
import xyz.breydan.uhc.UHC;
import xyz.breydan.uhc.game.Game;
import xyz.breydan.uhc.game.task.GameTask;

import static xyz.breydan.uhc.util.TranslateUtils.color;

@CommandInfo(value = "game", permission = "boys.game")
public class GameCommand implements Command {

    private BukkitTask gameTask;

    @CommandInfo(value = "start")
    public void start(CommandSender sender) {
        Game game = UHC.getInstance().getGame();
        gameTask = Bukkit.getScheduler().runTaskTimer(UHC.getInstance(), new GameTask(), 0, 20);
    }


}
