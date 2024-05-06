package chocola.chess;

import chocola.chess.piece.Piece;

import java.util.Optional;

abstract class StraightPieceValidator implements Validator {

    @Override
    public boolean isValid(ChessBoard chessBoard, Piece piece, Tile to) {
        checkType(piece);
        return checkBlocking(chessBoard, piece, to);
    }

    abstract void checkType(Piece piece);

    boolean checkBlocking(ChessBoard chessBoard, Piece piece, Tile to) {
        Tile position = piece.getPosition();
        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        int fileDirection = getDirection(pFile, tFile);
        int rankDirection = getDirection(pRank, tRank);
        for (int file = pFile + fileDirection, rank = pRank + rankDirection;
             file != tFile || rank != tRank;
             file += fileDirection, rank += rankDirection) {
            Optional<Piece> pieceOpt = chessBoard.getPiece(TileConverter.convertToTile(file, rank));
            if (pieceOpt.isPresent()) return false;
        }

        return true;
    }

    private int getDirection(int from, int to) {
        if (from == to) return 0;

        int diff = to - from;
        return diff / Math.abs(diff);
    }
}
