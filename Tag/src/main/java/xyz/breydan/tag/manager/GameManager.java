package xyz.breydan.tag.manager;

import xyz.breydan.tag.Tag;
import xyz.breydan.tag.game.Game;

public class GameManager {

    private final Tag tag;

    private Game game = new Game();

    public GameManager(Tag tag) {
        this.tag = tag;
    }

    public Game getGame() {
        return game;
    }
}
