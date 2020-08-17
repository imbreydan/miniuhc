package xyz.breydan.tag.settings;

import me.idriz.oss.config.annotation.Value;
import me.idriz.oss.config.yaml.YamlConfig;
import xyz.breydan.tag.Tag;

public class Settings {

    private final YamlConfig config;

    @Value("starting_border_size")
    public static int STARTING_BORDER_SIZE = 20_000;

    private Settings(Tag tag) {
        this.config = new YamlConfig(tag.getDataFolder(), "settings");
        config.addHook(this);
    }

    public static Settings init(Tag tag) {
        return new Settings(tag);
    }
}
