package xyz.breydan.tag.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.breydan.tag.manager.ProfileManager;
import xyz.breydan.tag.profile.Profile;
import xyz.breydan.tag.profile.ProfileState;

import static xyz.breydan.tag.util.TranslateUtils.color;

public class PlayerListener implements Listener {

    private final ProfileManager profileManager;

    public PlayerListener(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        profileManager.load(event.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(color("&7[&a+&7] &b" + event.getPlayer().getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(color("&7[&c-&7] &b" + player.getName()));
        Profile profile = profileManager.load(player.getUniqueId()).join();
        profileManager.unload(player.getUniqueId(), true);
        switch (profile.getProfileState()) {
            case GAME:
                //TODO: Leave server logic
                break;
            case LOBBY:
                break;
            case SPECTATING:
                break;
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(event.getEntity() instanceof Player);
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
                break;
            case GAME:
                event.setDamage(0.0);
                break;
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
                event.setDamage(0.0);
                break;
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Profile profile = profileManager.getProfile(player);
        event.setCancelled(!(profile.getProfileState() == ProfileState.GAME));
    }

}
