package xyz.breydan.uhc.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.selyu.ui.UserInterfaceProvider;
import xyz.breydan.uhc.UHC;
import xyz.breydan.uhc.board.BoardAdapter;
import xyz.breydan.uhc.game.Game;
import xyz.breydan.uhc.util.PlayerUtil;

import java.util.UUID;

import static xyz.breydan.uhc.util.TranslateUtils.color;

public class PlayerListener implements Listener {

    private final UserInterfaceProvider userInterfaceProvider;

    public PlayerListener() {
        userInterfaceProvider = new UserInterfaceProvider(UHC.getInstance(), 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerUtil.resetPlayer(player);
        event.setJoinMessage(color("&7[&a+&7] &b" + player.getName()));
        userInterfaceProvider.setBoard(event.getPlayer(), new BoardAdapter(player));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        //TODO: death logic
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(color("&7[&c-&7] &b" + player.getName()));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Game game = UHC.getInstance().getGame();
        if (event.getDamager() instanceof Player) {
            UUID damager = event.getDamager().getUniqueId();
            boolean sameTeam = game.getTeam(player.getUniqueId()).contains(damager);
            event.setCancelled(sameTeam);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Game game = UHC.getInstance().getGame();
        event.setCancelled(game.getGameState() != GameState.PLAYING);
    }

}
