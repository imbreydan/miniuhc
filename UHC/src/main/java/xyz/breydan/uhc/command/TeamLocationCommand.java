package xyz.breydan.uhc.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.breydan.uhc.UHC;

import java.util.UUID;

import static xyz.breydan.uhc.util.TranslateUtils.color;

@CommandInfo(value = "tl", aliases = "teamloc")
public class TeamLocationCommand implements Command {

    @Default
    public void execute(Player player) {
        Location loc = player.getLocation();
        for (UUID uuid : UHC.getInstance().getGame().getTeam(player.getUniqueId())) {
            Bukkit.getPlayer(uuid).sendMessage(color(String.format("%s's coords: %s, %s, %s", player.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
        }
    }
}
