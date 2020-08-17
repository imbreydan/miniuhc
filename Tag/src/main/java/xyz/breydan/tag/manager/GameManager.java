package xyz.breydan.tag.manager;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import xyz.breydan.tag.Tag;
import xyz.breydan.tag.game.Game;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static xyz.breydan.tag.database.Mongo.REPLACE_OPTIONS;

public class GameManager {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<String, Game> games = new ConcurrentHashMap<>();

    private final MongoCollection<Game> collection;

    public GameManager(Tag tag) {
        collection = tag.getMongo().getDatabase().getCollection("games", Game.class);
    }

    public CompletableFuture<Optional<Game>> getGameById(String id) {
        return supplyAsync(() -> {
            Game game = games.get(id);
            if (game != null) return Optional.of(game);
            game = collection.find(Filters.eq("gameId", id), Game.class).first();
            if (game == null) return Optional.empty();
            games.put(id, game);
            return Optional.of(game);
        }, executor);
    }

    public CompletableFuture<Void> save(Game game) {
        return runAsync(() -> {
            games.put(game.getGameId(), game);
            collection.findOneAndReplace(Filters.eq("gameId", game.getGameId()), game, REPLACE_OPTIONS);
        }, executor);
    }
}
