package chocola.chess.command;

import chocola.chess.ChessGame;

class NotationCommandFactory extends CommandFactory {

    @Override
    protected boolean validate(String input, ChessGame chessGame) {
        return ("기보".equals(input.trim()));
    }

    @Override
    protected Command create(ChessGame chessGame) {
        return chessGame.new NotationCommand();
    }
}
