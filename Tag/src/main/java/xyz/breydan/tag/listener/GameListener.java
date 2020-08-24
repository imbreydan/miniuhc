package xyz.breydan.tag.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.breydan.tag.Tag;

import java.util.concurrent.atomic.AtomicReference;

import static xyz.breydan.tag.util.TranslateUtils.color;

public class GameListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        System.out.println("onInteract");
        AtomicReference<Player> playerReference = new AtomicReference<>();
        if (event.getAction().name().contains("RIGHT")) {
            System.out.println("RIGHT");
            player.getWorld().getNearbyEntities(
                    player.getLocation(), 40000D, 25000D, 40000D,
                    entity -> !Tag.getInstance().getGameManager().getGame().getTeam(entity.getUniqueId()).contains(player.getUniqueId())
            ).stream().findFirst().ifPresent(entity -> playerReference.set((Player) entity));
        }
        player.sendMessage(color("&aCompass now pointing to " + playerReference.get().getName()));
    }
}
