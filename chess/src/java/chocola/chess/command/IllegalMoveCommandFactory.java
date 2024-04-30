package chocola.chess.command;

import chocola.chess.ChessGame;
import chocola.chess.ChessValidator;
import chocola.chess.Tile;

class IllegalMoveCommandFactory extends CommandFactory {

    @Override
    protected boolean validate(String input, ChessGame chessGame) {
        String[] split = input.trim().split(" ");

        if (split.length == 2) {
            String s1 = split[0].toUpperCase();
            String s2 = split[1].toUpperCase();

            try {
                Tile target = Tile.valueOf(s1);
                Tile to = Tile.valueOf(s2);

                ChessValidator validator = chessGame.getValidator();
                if (!validator.isValid(target, to)) return true;
            } catch (IllegalArgumentException ignored) {
            }
        }

        return false;
    }

    @Override
    protected Command create(ChessGame chessGame) {
        return new IllegalMoveCommand();
    }
}
