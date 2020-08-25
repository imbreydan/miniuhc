package xyz.breydan.tag;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.CommandRegistrar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.breydan.tag.command.*;
import xyz.breydan.tag.command.adapter.ProfileCommandAdapter;
import xyz.breydan.tag.database.Mongo;
import xyz.breydan.tag.game.Game;
import xyz.breydan.tag.listener.ChatListener;
import xyz.breydan.tag.listener.GameListener;
import xyz.breydan.tag.listener.PlayerListener;
import xyz.breydan.tag.manager.GameManager;
import xyz.breydan.tag.manager.ProfileManager;
import xyz.breydan.tag.profile.Profile;
import xyz.breydan.tag.settings.Settings;

public class Tag extends JavaPlugin {

    public static Tag instance;
    public static Tag getInstance() {
        return instance;
    }

    private CommandRegistrar provider;
    private Mongo mongo;
    private ProfileManager profileManager;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        instance = this;
        Settings.init(this);
        provider = new CommandRegistrar(this);
        mongo = new Mongo(Settings.URI, Settings.DB);
        profileManager = new ProfileManager(this);
        gameManager = new GameManager(this);
        registerListeners(
                new PlayerListener(profileManager),
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
            player.kickPlayer(ChatColor.RED + "Tag plugin loaded, please re-log.");
        }
        World world = Bukkit.getWorlds().get(0);
        world.setTime(0);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.getWorldBorder().setCenter(0, 0);
        world.getWorldBorder().setWarningDistance(0);
        world.getWorldBorder().setSize(20_000 * 2);
    }

    private void registerCommands(Command... commands) {
        CommandRegistrar.registerAdapter(Profile.class, new ProfileCommandAdapter());
        for (Command command : commands) {
            provider.registerCommand(command);
        }
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public Mongo getMongo() {
        return mongo;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
