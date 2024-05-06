package chocola.chess;

import chocola.chess.piece.Piece;
import chocola.chess.piece.Rook;

import java.util.Optional;

class RookValidator implements Validator {

    @Override
    public boolean isValid(ChessBoard chessBoard, Piece piece, Tile to) {
        if (!(piece instanceof Rook rook)) throw new IllegalArgumentException();
        return checkBlocking(chessBoard, rook, to);
    }

    private boolean checkBlocking(ChessBoard chessBoard, Rook rook, Tile to) {
        Tile position = rook.getPosition();
        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        if (pFile == tFile) return checkBlockingAtFile(chessBoard, pRank, tRank, tFile);
        else return checkBlockingAtRank(chessBoard, pFile, tFile, tRank);
    }

    private boolean checkBlockingAtFile(ChessBoard chessBoard, int from, int to, int file) {
        int direction = getDirection(from, to);
        for (int i = from + direction; i < to; i += direction) {
            Optional<Piece> pieceOpt = chessBoard.getPiece(TileConverter.convertToTile(file, i));
            if (pieceOpt.isPresent()) return false;
        }

        return true;
    }

    private boolean checkBlockingAtRank(ChessBoard chessBoard, int from, int to, int rank) {
        int direction = getDirection(from, to);
        for (int i = from + direction; i < to; i += direction) {
            Optional<Piece> pieceOpt = chessBoard.getPiece(TileConverter.convertToTile(i, rank));
            if (pieceOpt.isPresent()) return false;
        }

        return true;
    }

    private int getDirection(int from, int to) {
        int diff = to - from;
        return diff / Math.abs(diff);
    }
}
