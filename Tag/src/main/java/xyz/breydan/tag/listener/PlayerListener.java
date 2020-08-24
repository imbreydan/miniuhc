package xyz.breydan.tag.listener;

import com.destroystokyo.paper.Title;
import org.bukkit.ChatColor;
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
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.selyu.ui.UserInterfaceProvider;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.board.BoardAdapter;
import xyz.breydan.tag.game.Game;
import xyz.breydan.tag.game.GameState;
import xyz.breydan.tag.manager.ProfileManager;
import xyz.breydan.tag.profile.Profile;
import xyz.breydan.tag.profile.ProfileState;
import xyz.breydan.tag.util.PlayerUtil;

import java.util.UUID;

import static xyz.breydan.tag.util.TranslateUtils.color;

public class PlayerListener implements Listener {

    private final ProfileManager profileManager;

    private final UserInterfaceProvider userInterfaceProvider;

    public PlayerListener(ProfileManager profileManager) {
        this.profileManager = profileManager;
        userInterfaceProvider = new UserInterfaceProvider(Tag.getInstance(), 20);
    }

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        profileManager.load(event.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerUtil.resetPlayer(player);
        if (Tag.getInstance().getGameManager().getGame().getGameState() == GameState.LOBBY) {
            player.teleport(new Location(player.getWorld(), 0, 70, 0));
            Tag.getInstance().getGameManager().getGame().getTeam(player.getUniqueId()).remove(player.getUniqueId());
        }
        event.setJoinMessage(color("&7[&a+&7] &b" + player.getName()));
        userInterfaceProvider.setBoard(event.getPlayer(), new BoardAdapter(player));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Profile profile = profileManager.getProfile(player);
        profile.setProfileState(ProfileState.SPECTATING);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(color("&7[&c-&7] &b" + player.getName()));
        profileManager.unload(player.getUniqueId(), true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Profile profile = profileManager.getProfile(player);
        switch (profile.getProfileState()) {
            case LOBBY:
            case SPECTATING:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Profile profile = profileManager.getProfile(player);
        switch (profile.getProfileState()) {
            case LOBBY:
            case SPECTATING:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        Profile profile = profileManager.getProfile(player);
        switch (profile.getProfileState()) {
            case LOBBY:
            case SPECTATING:
                event.setCancelled(true);
                break;
            case GAME:
                if (event.getDamager() instanceof Player) {
                    UUID damager = event.getDamager().getUniqueId();
                    Game game = Tag.getInstance().getGameManager().getGame();
                    boolean sameTeam = game.getTeam(player.getUniqueId()).contains(damager);
                    event.setCancelled(sameTeam);
                }
                break;
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = profileManager.getProfile(player);
        event.setCancelled(!(profile.getProfileState() == ProfileState.GAME));
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Profile profile = profileManager.getProfile(player);
        switch (profile.getProfileState()) {
            case LOBBY:
            case SPECTATING:
                event.setCancelled(true);
        }
    }

}
