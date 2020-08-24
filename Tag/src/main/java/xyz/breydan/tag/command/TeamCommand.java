package xyz.breydan.tag.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.Argument;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.game.Game;
import xyz.breydan.tag.profile.Profile;

import static xyz.breydan.tag.util.TranslateUtils.color;

@CommandInfo(value = "team")
public class TeamCommand implements Command {

    @Override
    public String[] getUsage() {
        return color("&cUsage:", "&c - /team runners", "&c - /team hunters");
    }

    @Default
    public void execute(Player player, @Argument(completions = {"runners", "hunters"}) String team) {
        player.sendMessage(getUsage());
    }

    @CommandInfo(value = "runners", aliases = "runner")
    public void joinRunners(Player player) {
        Game game = Tag.getInstance().getGameManager().getGame();
        game.addRunner(player);
        player.sendMessage(color("&aYou have joined the runners."));
    }

    @CommandInfo(value = "hunters", aliases = "hunter")
    public void joinHunters(Player player) {
        Game game = Tag.getInstance().getGameManager().getGame();
        game.addHunter(player);
        player.sendMessage(color("&aYou have joined the hunters."));
    }
}
