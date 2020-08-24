package xyz.breydan.tag.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.game.Game;
import xyz.breydan.tag.game.GameState;
import xyz.breydan.tag.game.task.GameTask;
import xyz.breydan.tag.settings.Settings;
import xyz.breydan.tag.util.ScatterUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static xyz.breydan.tag.util.TranslateUtils.color;

@CommandInfo(value = "game", permission = "boys.game")
public class GameCommand implements Command {

    @Default
    public void execute(CommandSender sender) {
        sender.sendMessage(color("&cUsage:\n" +
                "   &c/game start - Starts the game.\n" +
                "   &c/game nextborder - Runs the code for cycling to the next world border.\n" +
                "   &c/game scatter - Scatters the players within the current border."));
    }

    private BukkitTask gameTask;

    @CommandInfo(value = "start")
    public void start(CommandSender sender) {
        Game game = Tag.getInstance().getGameManager().getGame();
        game.setGameState(GameState.SCATTERING);
        ScatterUtil.scatter(game.getRunners(), true, true);
        ScatterUtil.scatter(game.getHunters(), false, true);
        game.setGameState(GameState.PLAYING);
        gameTask = Bukkit.getScheduler().runTaskTimer(Tag.getInstance(), new GameTask(), 0, 20);
    }

    @CommandInfo(value = "stop")
    public void stop(CommandSender sender) {
        Game game = Tag.getInstance().getGameManager().getGame();
        gameTask.cancel();
        game.setGameState(GameState.FINISHED);
        for (UUID uuid : game.getAllPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            World world = player.getWorld();
            player.teleport(new Location(world, 0, world.getHighestBlockYAt(0, 0) + 2,  0));
        }
    }

    @CommandInfo(value = "nextborder")
    public void border(CommandSender sender) {
        Game game = Tag.getInstance().getGameManager().getGame();
        World world = Bukkit.getWorlds().get(0);
        game.setBorder(game.getNextBorder());
        game.setBorderTime(60 * Settings.BORDER_CHANGE_INTERVAL_MINS);
        world.getWorldBorder().setSize(game.getNextBorder() * 2);
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Item) entity.remove();
        }
        Bukkit.broadcastMessage(color("&6The border has been shrunk to &e" + game.getBorder() + "&6."));
    }

    @CommandInfo(value = "scatter")
    public void scatter(CommandSender sender) {
        Game game = Tag.getInstance().getGameManager().getGame();
        ScatterUtil.scatter(game.getRunners(), true, false);
        ScatterUtil.scatter(game.getHunters(), false, false);
    }

}
