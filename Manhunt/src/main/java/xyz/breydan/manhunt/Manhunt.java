package xyz.breydan.manhunt;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.CommandRegistrar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.breydan.manhunt.command.*;
import xyz.breydan.manhunt.listener.ChatListener;
import xyz.breydan.manhunt.listener.GameListener;
import xyz.breydan.manhunt.listener.PlayerListener;
import xyz.breydan.manhunt.manager.GameManager;
import xyz.breydan.manhunt.settings.Settings;

public class Manhunt extends JavaPlugin {

    public static Manhunt instance;
    public static Manhunt getInstance() {
        return instance;
    }

    private CommandRegistrar provider;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        Settings.init(this);
        provider = new CommandRegistrar(this);
        gameManager = new GameManager(this);
        registerListeners(
                new PlayerListener(),
                new ChatListener(),
                new GameListener()
        );
        registerCommands(
                new GameCommand(),
                new TeamCommand(),
                new TeamLocationCommand(),
                new TeamChatCommand(),
                new BackpackCommand()
        );
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(ChatColor.RED + "Manhunt plugin loaded, please re-log.");
        }
        World world = Bukkit.getWorlds().get(0);
        world.setTime(0);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.getWorldBorder().setCenter(0, 0);
        world.getWorldBorder().setWarningDistance(0);
        world.getWorldBorder().setSize(20_000 * 2);
    }

    private void registerCommands(Command... commands) {
        for (Command command : commands) {
            provider.registerCommand(command);
        }
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
