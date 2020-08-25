package xyz.breydan.tag.command;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.annotation.CommandInfo;
import me.idriz.oss.commands.annotation.Default;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.game.Game;

@CommandInfo(value = "bp", aliases = "backpack")
public class BackpackCommand implements Command {

    @Default
    public void execute(Player player) {
        if (Tag.getInstance().getGameManager().getGame().getRunners().contains(player.getUniqueId())) {
            player.openInventory(Tag.getInstance().getGameManager().getGame().getRunnerBackpack());
        } else if (Tag.getInstance().getGameManager().getGame().getHunters().contains(player.getUniqueId())) {
            player.openInventory(Tag.getInstance().getGameManager().getGame().getHunterBackpack());
        }
    }
}
