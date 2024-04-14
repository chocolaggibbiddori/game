package chocola.game;

import chocola.common.IOProcessor;
import chocola.interfaces.Game;
import chocola.interfaces.GameManager;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Game> gameList = GameManager.getList();
        StringBuilder gameListStringBuilder = new StringBuilder();
        for (int i = 0; i < gameList.size(); i++) {
            Game game = gameList.get(i);
            gameListStringBuilder
                    .append("%d: %s".formatted(i + 1, game.getName()))
                    .append(" | ");
        }

        int lastIdx = gameListStringBuilder.lastIndexOf(" | ");
        IOProcessor.println(gameListStringBuilder.substring(0, lastIdx));

        int number = readGameNumber(gameList.size());
        Game game = gameList.get(number);
        play(game);
    }

    private static int readGameNumber(int max) {
        IOProcessor.println("게임을 선택하세요.");
        String input = IOProcessor.readLine();

        while (!isInt(input) || !isValidRange(input, max)) {
            IOProcessor.println("목록 내 숫자를 입력해 주세요.");
            input = IOProcessor.readLine();
        }

        return Integer.parseInt(input) - 1;
    }

    private static boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidRange(String input, int max) {
        int i = Integer.parseInt(input);
        return i >= 1 && i <= max;
    }

    private static void play(Game game) {
        game.start();
        game.end();
    }
}
