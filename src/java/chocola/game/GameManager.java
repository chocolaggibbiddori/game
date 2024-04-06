package chocola.game;

import chocola.interfaces.Game;

import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

class GameManager {

    private GameManager() {
    }

    static Optional<Game> findGame(String gameName) {
        Objects.requireNonNull(gameName, "Argument 'gameName' is null");

        ServiceLoader<Game> loader = ServiceLoader.load(Game.class);
        for (Game game : loader)
            if (gameName.equalsIgnoreCase(game.getName())) return Optional.of(game);

        return Optional.empty();
    }
}
