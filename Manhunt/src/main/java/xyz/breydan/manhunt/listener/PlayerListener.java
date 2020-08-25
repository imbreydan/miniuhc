package xyz.breydan.manhunt.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.selyu.ui.UserInterfaceProvider;
import xyz.breydan.manhunt.Manhunt;
import xyz.breydan.manhunt.board.BoardAdapter;
import xyz.breydan.manhunt.game.Game;
import xyz.breydan.manhunt.game.GameState;
import xyz.breydan.manhunt.util.PlayerUtil;

import java.util.UUID;

import static xyz.breydan.manhunt.util.TranslateUtils.color;

public class PlayerListener implements Listener {

    private final UserInterfaceProvider userInterfaceProvider;

    public PlayerListener() {
        userInterfaceProvider = new UserInterfaceProvider(Manhunt.getInstance(), 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerUtil.resetPlayer(player);
        if (Manhunt.getInstance().getGameManager().getGame().getGameState() == GameState.LOBBY) {
            player.teleport(new Location(player.getWorld(), 0, 70, 0));
            Manhunt.getInstance().getGameManager().getGame().getTeam(player.getUniqueId()).remove(player.getUniqueId());
        }
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
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        event.setCancelled(Manhunt.getInstance().getGameManager().getGame().getGameState() != GameState.PLAYING);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        event.setCancelled(Manhunt.getInstance().getGameManager().getGame().getGameState() != GameState.PLAYING);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Game game = Manhunt.getInstance().getGameManager().getGame();
        if (game.getGameState() == GameState.PLAYING) {
            if (event.getDamager() instanceof Player) {
                UUID damager = event.getDamager().getUniqueId();
                boolean sameTeam = game.getTeam(player.getUniqueId()).contains(damager);
                event.setCancelled(sameTeam);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Game game = Manhunt.getInstance().getGameManager().getGame();
        event.setCancelled(game.getGameState() != GameState.PLAYING);
    }

}
