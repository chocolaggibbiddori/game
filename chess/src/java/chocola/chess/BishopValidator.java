package chocola.chess;

import chocola.chess.piece.Bishop;
import chocola.chess.piece.Piece;

class BishopValidator extends StraightPieceValidator {

    @Override
    void checkType(Piece piece) {
        if (!(piece instanceof Bishop)) throw new IllegalArgumentException();
    }
}
