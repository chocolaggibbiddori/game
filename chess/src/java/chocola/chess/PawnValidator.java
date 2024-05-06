package chocola.chess;

import chocola.chess.piece.Pawn;
import chocola.chess.piece.Piece;

class PawnValidator implements Validator {

    @Override
    public boolean isValid(ChessBoard chessBoard, Piece piece, Tile to) {
        if (!(piece instanceof Pawn pawn)) throw new IllegalArgumentException();
        return canMoveTo(chessBoard, pawn, to);
    }

    private boolean canMoveTo(ChessBoard chessBoard, Pawn pawn, Tile to) {
        if (pawn.canAttackTo(to)) return true;
        return chessBoard.getPiece(to).isEmpty();
    }
}
