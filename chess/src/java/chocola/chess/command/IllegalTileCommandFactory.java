package chocola.chess.command;

import chocola.chess.ChessGame;
import chocola.chess.Tile;

class IllegalTileCommandFactory extends CommandFactory {

    @Override
    protected boolean validate(String input, ChessGame chessGame) {
        String[] split = input.trim().split(" ");
        if (split.length == 2) {
            String s1 = split[0].toUpperCase();
            String s2 = split[1].toUpperCase();

            try {
                Tile.valueOf(s1);
                Tile.valueOf(s2);
            } catch (IllegalArgumentException e) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected Command create(ChessGame chessGame) {
        return new IllegalTileCommand();
    }
}
