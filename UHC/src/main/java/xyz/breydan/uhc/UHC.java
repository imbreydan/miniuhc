package xyz.breydan.uhc;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.CommandRegistrar;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.breydan.uhc.command.*;
import xyz.breydan.uhc.game.Game;
import xyz.breydan.uhc.listener.ChatListener;
import xyz.breydan.uhc.listener.PlayerListener;
import xyz.breydan.uhc.settings.Settings;

public class UHC extends JavaPlugin {

    public static UHC instance;
    public static UHC getInstance() {
        return instance;
    }

    private CommandRegistrar provider;
    private final Game game = new Game();

    @Override
    public void onEnable() {
        instance = this;
        Settings.init(this);
        provider = new CommandRegistrar(this);
        registerListeners(
                new PlayerListener(),
                new ChatListener()
        );
        registerCommands(
                new GameCommand(),
                new TeamCommand(),
                new TeamLocationCommand(),
                new TeamChatCommand(),
                new BackpackCommand()
        );
        World world = Bukkit.getWorlds().get(0);
        world.setTime(0);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.getWorldBorder().setWarningDistance(0);
        world.getWorldBorder().setCenter(0, 0);
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

    public Game getGame() {
        return game;
    }

}
