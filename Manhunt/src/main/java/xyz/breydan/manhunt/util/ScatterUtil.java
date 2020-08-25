package xyz.breydan.manhunt.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.breydan.manhunt.Manhunt;
import xyz.breydan.manhunt.game.Game;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class ScatterUtil {

    public static Random r = new Random();

    private static Game game = Manhunt.getInstance().getGameManager().getGame();
    private static World world = Bukkit.getWorlds().get(0);
    private static Location lastScatter = new Location(world, 0, world.getHighestBlockYAt(0, 0) + 2, 0);

    public static void scatter(List<UUID> players, boolean runners, boolean firstScatter) {
        Location location = getSuitableLocation();
        location.getWorld().getChunkAtAsync(location, true).thenRun(() -> {
            players.stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(player -> {
                player.teleport(location);
                if (firstScatter) {
                    player.getInventory().clear();
                    player.getInventory().setItem(8, new ItemStack(Material.COOKED_BEEF, 16));
                    if (runners) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 1));
                    } else {
                        player.getInventory().setItem(7, ItemBuilder.create(Material.COMPASS).withName("&cEnemy Tracking Compass").build());
                    }
                }
            });
        });
    }

    public static Location getSuitableLocation() {
        int max = game.getBorder();
        int min = -game.getBorder();

        int x = r.nextInt(max - min) + min;
        int z = r.nextInt(max - min) + min;

        Location location = new Location(world, x, world.getHighestBlockYAt(x, z) + 2, z);

        while (location.getBlock().getRelative(0, -2, 0).getType() == Material.LAVA
                || location.getBlock().getRelative(0, -2, 0).getType() == Material.WATER) {
            x = r.nextInt(max - min) + min;
            z = r.nextInt(max - min) + min;
            location = new Location(world, x, world.getHighestBlockYAt(x, z) + 2, z);
        }
        lastScatter = location;
        return location;
    }

}
