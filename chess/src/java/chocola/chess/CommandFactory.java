package chocola.chess;

import chocola.chess.ChessGame.IllegalCommand.Type;

class CommandFactory {

    private CommandFactory() {
    }

    static Command createCommand(String input, ChessGame chessGame) {
        input = input.trim();

        if ("기보".equals(input)) return chessGame.new NotationCommand();
        if ("기권".equals(input)) return chessGame.new GiveupCommand();

        String[] split = input.split(" ");
        if (split.length == 2) {
            String s1 = split[0].toUpperCase();
            String s2 = split[1].toUpperCase();

            try {
                Tile target = Tile.valueOf(s1);
                Tile to = Tile.valueOf(s2);

                ChessValidator validator = chessGame.getValidator();
                if (validator.isValid(target, to)) return chessGame.new MoveCommand(target, to);

                return new ChessGame.IllegalCommand(Type.MOVE);
            } catch (IllegalArgumentException e) {
                return new ChessGame.IllegalCommand(Type.TILE);
            }
        } else {
            return new ChessGame.IllegalCommand(Type.INPUT);
        }
    }
}
