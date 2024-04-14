package chocola.interfaces;

import java.util.*;

public class GameManager {

    private GameManager() {
    }

    public static Optional<Game> findGame(String gameName) {
        Objects.requireNonNull(gameName, "Argument 'gameName' is null");

        ServiceLoader<Game> loader = ServiceLoader.load(Game.class);
        for (Game game : loader)
            if (gameName.equalsIgnoreCase(game.getName())) return Optional.of(game);

        return Optional.empty();
    }

    public static List<Game> getList() {
        List<Game> result = new ArrayList<>();

        ServiceLoader<Game> loader = ServiceLoader.load(Game.class);
        for (Game game : loader) result.add(game);

        return result;
    }
}
