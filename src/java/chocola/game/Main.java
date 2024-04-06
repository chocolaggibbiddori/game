package chocola.game;

import chocola.interfaces.Game;
import chocola.interfaces.GameManager;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        String gameName = "omok";
        Optional<Game> gameOpt = GameManager.findGame(gameName);

        if (gameOpt.isEmpty()) throw new IllegalStateException();

        Game game = gameOpt.get();
        play(game);
    }

    private static void play(Game game) {
        game.start();
        game.end();
    }
}
