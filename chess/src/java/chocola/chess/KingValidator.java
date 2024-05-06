package chocola.chess;

import chocola.chess.ChessBoard.ChessValidator;
import chocola.chess.piece.King;
import chocola.chess.piece.Piece;
import chocola.chess.piece.Rook;

import java.util.Optional;

class KingValidator implements Validator {

    @Override
    public boolean isValid(ChessBoard chessBoard, Piece piece, Tile to) {
        if (!(piece instanceof King king)) throw new IllegalArgumentException();
        return canCastling(chessBoard, king, to);
    }

    private boolean canCastling(ChessBoard chessBoard, King king, Tile to) {
        if (king.isMoved()) return false;

        Tile position = king.getPosition();
        if (king.team == Team.WHITE) {
            if (position != Tile.E1) return false;
            if (ChessValidator.isChecked(chessBoard, king, Tile.E1)) return false;
            if (to != Tile.C1 && to != Tile.G1) return false;
            if (to == Tile.C1) {
                if (ChessValidator.isChecked(chessBoard, king, Tile.D1)) return false;
            } else {
                if (ChessValidator.isChecked(chessBoard, king, Tile.F1)) return false;
            }
        } else {
            if (position != Tile.E8) return false;
            if (ChessValidator.isChecked(chessBoard, king, Tile.E8)) return false;
            if (to != Tile.C8 && to != Tile.G8) return false;
            if (to == Tile.C8) {
                if (ChessValidator.isChecked(chessBoard, king, Tile.D8)) return false;
            } else {
                if (ChessValidator.isChecked(chessBoard, king, Tile.F8)) return false;
            }
        }

        Optional<Piece> toPieceOpt = chessBoard.getPiece(to);
        if (toPieceOpt.isEmpty()) return false;

        Piece toPiece = toPieceOpt.get();
        if (!(toPiece instanceof Rook rook)) return false;
        return !rook.isMoved();
    }
}
