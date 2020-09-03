package xyz.breydan.tag;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.breydan.tag.listener.PlayerListener;

public class Tag extends JavaPlugin {

    private static Tag instance;

    public static Tag getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
