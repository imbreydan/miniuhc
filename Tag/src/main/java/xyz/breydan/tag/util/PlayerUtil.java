package xyz.breydan.tag.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerUtil {

    public static void resetPlayer(Player player) {
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setLevel(0);
        player.setHealth(20.0);
        player.setFireTicks(0);
        player.setExp(0);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }
}
