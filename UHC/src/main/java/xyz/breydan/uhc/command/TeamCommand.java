package xyz.breydan.uhc.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import org.bukkit.entity.Player;
import xyz.breydan.uhc.UHC;
import xyz.breydan.uhc.game.Game;

import static xyz.breydan.uhc.util.TranslateUtils.color;

@CommandInfo(value = "team")
public class TeamCommand implements Command {

    @CommandInfo(value = "alpha")
    public void joinAlpha(Player player) {
        Game game = UHC.getInstance().getGame();
        game.addAlpha(player);
        player.sendMessage(color("&aJoined team #1."));
    }

    @CommandInfo(value = "beta")
    public void joinBeta(Player player) {
        Game game = UHC.getInstance().getGame();
        game.addBeta(player);
        player.sendMessage(color("&aJoined team #2."));
    }
}
