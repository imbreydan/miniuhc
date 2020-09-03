package xyz.breydan.uhc.settings;

import me.idriz.oss.config.annotation.Value;
import me.idriz.oss.config.yaml.YamlConfig;
import xyz.breydan.uhc.UHC;

public class Settings {

    private final YamlConfig config;

    @Value("mongo_uri")
    public static String URI = "mongodb://localhost:27017/tag";

    @Value("mongo_db")
    public static String DB = "tag";

    @Value("border_change_interval_mins")
    public static int BORDER_CHANGE_INTERVAL_MINS = 10;

    private Settings(UHC UHC) {
        this.config = new YamlConfig(UHC.getDataFolder(), "settings");
        config.addHook(this);
    }

    public static Settings init(UHC UHC) {
        return new Settings(UHC);
    }
}
