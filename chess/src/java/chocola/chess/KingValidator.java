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

        Tile kingPosition = king.getPosition();
        Tile rookPosition;
        if (king.team == Team.WHITE) {
            if (kingPosition != Tile.E1) return false;
            if (ChessValidator.isChecked(chessBoard, kingPosition, Tile.E1)) return false;
            if (to != Tile.C1 && to != Tile.G1) return false;
            if (to == Tile.C1) {
                if (chessBoard.getPiece(Tile.D1).isPresent()) return false;
                if (chessBoard.getPiece(Tile.C1).isPresent()) return false;
                if (chessBoard.getPiece(Tile.B1).isPresent()) return false;
                if (ChessValidator.isChecked(chessBoard, kingPosition, Tile.D1)) return false;
                rookPosition = Tile.A1;
            } else {
                if (chessBoard.getPiece(Tile.F1).isPresent()) return false;
                if (chessBoard.getPiece(Tile.G1).isPresent()) return false;
                if (ChessValidator.isChecked(chessBoard, kingPosition, Tile.F1)) return false;
                rookPosition = Tile.H1;
            }
        } else {
            if (kingPosition != Tile.E8) return false;
            if (ChessValidator.isChecked(chessBoard, kingPosition, Tile.E8)) return false;
            if (to != Tile.C8 && to != Tile.G8) return false;
            if (to == Tile.C8) {
                if (chessBoard.getPiece(Tile.D8).isPresent()) return false;
                if (chessBoard.getPiece(Tile.C8).isPresent()) return false;
                if (chessBoard.getPiece(Tile.B8).isPresent()) return false;
                if (ChessValidator.isChecked(chessBoard, kingPosition, Tile.D8)) return false;
                rookPosition = Tile.A8;
            } else {
                if (chessBoard.getPiece(Tile.F8).isPresent()) return false;
                if (chessBoard.getPiece(Tile.G8).isPresent()) return false;
                if (ChessValidator.isChecked(chessBoard, kingPosition, Tile.F8)) return false;
                rookPosition = Tile.H8;
            }
        }

        Optional<Piece> toPieceOpt = chessBoard.getPiece(rookPosition);
        if (toPieceOpt.isEmpty()) return false;

        Piece toPiece = toPieceOpt.get();
        if (!(toPiece instanceof Rook rook)) return false;
        return !rook.isMoved();
    }
}
