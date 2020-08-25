package xyz.breydan.manhunt.settings;

import me.idriz.oss.config.annotation.Value;
import me.idriz.oss.config.yaml.YamlConfig;
import xyz.breydan.manhunt.Manhunt;

public class Settings {

    private final YamlConfig config;

    @Value("mongo_uri")
    public static String URI = "mongodb://localhost:27017/tag";

    @Value("mongo_db")
    public static String DB = "tag";

    @Value("border_change_interval_mins")
    public static int BORDER_CHANGE_INTERVAL_MINS = 10;

    private Settings(Manhunt manhunt) {
        this.config = new YamlConfig(manhunt.getDataFolder(), "settings");
        config.addHook(this);
    }

    public static Settings init(Manhunt manhunt) {
        return new Settings(manhunt);
    }
}
