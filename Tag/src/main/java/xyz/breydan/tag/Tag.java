package xyz.breydan.tag;

import me.idriz.oss.commands.Command;
import me.idriz.oss.commands.CommandRegistrar;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.breydan.tag.database.Mongo;
import xyz.breydan.tag.listener.ChatListener;
import xyz.breydan.tag.listener.PlayerListener;
import xyz.breydan.tag.manager.GameManager;
import xyz.breydan.tag.manager.ProfileManager;
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
        provider = new CommandRegistrar(this);
        mongo = new Mongo("mongodb://localhost:27017/tag", "tag");
        profileManager = new ProfileManager(this);
        gameManager = new GameManager(this);
        Settings.init(this);
        registerListeners(
                new PlayerListener(profileManager),
                new ChatListener()
        );
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
