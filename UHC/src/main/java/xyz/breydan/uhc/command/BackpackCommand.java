package xyz.breydan.uhc.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.entity.Player;
import xyz.breydan.uhc.UHC;

@CommandInfo(value = "bp", aliases = "backpack")
public class BackpackCommand implements Command {

    @Default
    public void execute(Player player) {
        if (UHC.getInstance().getGame().getBetas().contains(player.getUniqueId())) {
            player.openInventory(UHC.getInstance().getGame().getAlphaBackpack());
        } else if (UHC.getInstance().getGame().getAlphas().contains(player.getUniqueId())) {
            player.openInventory(UHC.getInstance().getGame().getBetaBackpack());
        }
    }
}
