package xyz.breydan.tag.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.breydan.tag.game.Game;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static xyz.breydan.tag.util.TranslateUtils.color;

@CommandInfo(value = "game", aliases = "tag", permission = "boys.game")
public class GameCommand implements Command {

    @Override
    public String[] getUsage() {
        return color(
                "&cUsage:",
                "",
                "&c/game start",
                "&c/game addrunner <player>",
                "&c/game info",
                "&c/game stop"
        );
    }

    @Default
    public void onExecute(CommandSender sender) {
        sender.sendMessage(getUsage());
    }

    @CommandInfo(value = "start", permission = "boys.game.start")
    public void start(CommandSender sender) {
        List<UUID> players = Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList());;
        new Game(players);
    }

}
