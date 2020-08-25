package xyz.breydan.tag.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.adapter.annotation.Text;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.breydan.tag.Tag;

import java.util.UUID;

import static xyz.breydan.tag.util.TranslateUtils.color;

@CommandInfo(value = "teamchat", aliases = "tc")
public class TeamChatCommand implements Command {

    @Default
    public void execute(Player player, @Text String message) {
        for (UUID uuid : Tag.getInstance().getGameManager().getGame().getTeam(player.getUniqueId())) {
            Bukkit.getPlayer(uuid).sendMessage(color("&6[Team] &e" + player.getName()) + ChatColor.RESET + " " + message);
        }
    }
}
