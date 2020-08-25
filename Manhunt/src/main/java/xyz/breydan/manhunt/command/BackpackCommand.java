package xyz.breydan.manhunt.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.entity.Player;
import xyz.breydan.manhunt.Manhunt;

@CommandInfo(value = "bp", aliases = "backpack")
public class BackpackCommand implements Command {

    @Default
    public void execute(Player player) {
        if (Manhunt.getInstance().getGameManager().getGame().getRunners().contains(player.getUniqueId())) {
            player.openInventory(Manhunt.getInstance().getGameManager().getGame().getRunnerBackpack());
        } else if (Manhunt.getInstance().getGameManager().getGame().getHunters().contains(player.getUniqueId())) {
            player.openInventory(Manhunt.getInstance().getGameManager().getGame().getHunterBackpack());
        }
    }
}
