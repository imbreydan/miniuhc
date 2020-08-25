package xyz.breydan.manhunt.manager;

import xyz.breydan.manhunt.Manhunt;
import xyz.breydan.manhunt.game.Game;

public class GameManager {

    private final Manhunt manhunt;

    private Game game = new Game();

    public GameManager(Manhunt manhunt) {
        this.manhunt = manhunt;
    }

    public Game getGame() {
        return game;
    }
}
