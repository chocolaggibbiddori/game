package chocola.chess;

import chocola.chess.piece.Knight;
import chocola.chess.piece.Piece;

class KnightValidator implements Validator {

    @Override
    public boolean isValid(ChessBoard chessBoard, Piece piece, Tile to) {
        if (piece instanceof Knight) throw new IllegalArgumentException();
        return true;
    }
}
