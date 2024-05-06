package chocola.chess;

import chocola.chess.piece.Piece;
import chocola.chess.piece.Queen;

class QueenValidator extends StraightPieceValidator {

    @Override
    void checkType(Piece piece) {
        if (!(piece instanceof Queen)) throw new IllegalArgumentException();
    }
}
