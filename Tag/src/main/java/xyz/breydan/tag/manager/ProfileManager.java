package xyz.breydan.tag.manager;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bukkit.entity.Player;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.profile.Profile;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static xyz.breydan.tag.database.Mongo.REPLACE_OPTIONS;

public class ProfileManager {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final Map<UUID, Profile> profiles = new ConcurrentHashMap<>();
    private final MongoCollection<Profile> collection;

    public ProfileManager(Tag tag) {
        this.collection = tag.getMongo().getDatabase().getCollection("profiles", Profile.class);
    }

    public CompletableFuture<Profile> load(UUID uuid) {
        return findByUUID(uuid)
                .thenApply(result -> {
                    Profile profile;
                    if (result.isEmpty()) {
                        profile = new Profile(uuid);
                        save(profile).join();
                    } else profile = result.get();
                    return profiles.put(uuid, profile);
                });
    }

    public void unload(UUID uuid, boolean save) {
        runAsync(() -> {
            Profile profile = profiles.remove(uuid);
            if (profile == null) return;
            if (save) save(profile);
        }, executor);
    }

    public CompletableFuture<Optional<Profile>> findByUUID(UUID uuid) {
        return supplyAsync(() -> {
            Profile profile = profiles.get(uuid);
            if (profile != null) return Optional.of(profile);
            profile = collection.find(Filters.eq("uuid", uuid.toString()), Profile.class).first();
            if (profile == null) return Optional.empty();
            profiles.put(uuid, profile);
            return Optional.of(profile);
        }, executor);
    }

    public CompletableFuture<Profile> save(Profile profile) {
        return supplyAsync(() -> {
            profiles.put(profile.getUuid(), profile);
            return collection.findOneAndReplace(Filters.eq("uuid", profile.getUuid().toString()), profile, REPLACE_OPTIONS);
        }, executor);
    }

    public Profile getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }

    public Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public Map<UUID, Profile> getProfiles() {
        return profiles;
    }
}
