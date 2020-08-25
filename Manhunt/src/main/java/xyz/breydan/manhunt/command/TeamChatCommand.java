package xyz.breydan.manhunt.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.adapter.annotation.Text;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.breydan.manhunt.Manhunt;

import java.util.UUID;

import static xyz.breydan.manhunt.util.TranslateUtils.color;

@CommandInfo(value = "teamchat", aliases = "tc")
public class TeamChatCommand implements Command {

    @Default
    public void execute(Player player, @Text String message) {
        for (UUID uuid : Manhunt.getInstance().getGameManager().getGame().getTeam(player.getUniqueId())) {
            Bukkit.getPlayer(uuid).sendMessage(color("&6[Team] &e" + player.getName()) + ChatColor.RESET + " " + message);
        }
    }
}
