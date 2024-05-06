package chocola.chess;

import chocola.chess.piece.Piece;
import chocola.chess.piece.Rook;

class RookValidator extends StraightPieceValidator {

    @Override
    void checkType(Piece piece) {
        if (!(piece instanceof Rook)) throw new IllegalArgumentException();
    }
}
