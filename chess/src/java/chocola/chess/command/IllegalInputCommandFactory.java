package chocola.chess.command;

import chocola.chess.ChessGame;

class IllegalInputCommandFactory extends CommandFactory {

    @Override
    protected boolean validate(String input, ChessGame chessGame) {
        return input.trim().split(" ").length != 2;
    }

    @Override
    protected Command create(ChessGame chessGame) {
        return new IllegalInputCommand();
    }
}
