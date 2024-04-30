package chocola.chess.command;

import chocola.chess.ChessGame;

class GiveupCommandFactory extends CommandFactory {

    @Override
    protected boolean validate(String input, ChessGame chessGame) {
        return "기권".equals(input.trim());
    }

    @Override
    protected Command create(ChessGame chessGame) {
        return chessGame.new GiveupCommand();
    }
}
