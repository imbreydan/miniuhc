package xyz.breydan.manhunt.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.breydan.manhunt.Manhunt;

import java.util.concurrent.atomic.AtomicReference;

import static xyz.breydan.manhunt.util.TranslateUtils.color;

public class GameListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = event.getPlayer();
            if (event.getItem() != null && event.getItem().getType() != Material.COMPASS) return;
            AtomicReference<Player> playerReference = new AtomicReference<>();
            player.getWorld().getNearbyEntities(
                    player.getLocation(), 40000D, 25000D, 40000D,
                    entity -> entity instanceof Player
                            && entity.getUniqueId() != player.getUniqueId()
                            && !Manhunt.getInstance().getGameManager().getGame().getTeam(entity.getUniqueId()).contains(player.getUniqueId())
            ).stream().findFirst().ifPresent(entity -> playerReference.set((Player) entity));

            if (playerReference.get() == null) {
                player.sendMessage(color("&cThere is no available player to track."));
                return;
            }
            Player target = playerReference.get();
            player.setCompassTarget(target.getLocation().clone());
            player.sendMessage(color("&aCompass now pointing to " + target.getName() + ". (y=" + target.getLocation().getBlockY() + ")"));
        }
    }
}
