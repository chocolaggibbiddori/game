package chocola.chess;

import chocola.chess.piece.Bishop;
import chocola.chess.piece.Piece;

import java.util.Optional;

class BishopValidator implements Validator {

    @Override
    public boolean isValid(ChessBoard chessBoard, Piece piece, Tile to) {
        if (!(piece instanceof Bishop bishop)) throw new IllegalArgumentException();
        return checkBlocking(chessBoard, bishop, to);
    }

    private boolean checkBlocking(ChessBoard chessBoard, Bishop bishop, Tile to) {
        Tile position = bishop.getPosition();
        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        int fileDirection = getDirection(pFile, tFile);
        int rankDirection = getDirection(pRank, tRank);
        for (int file = pFile + fileDirection, rank = pRank + rankDirection;
             file < tFile;
             file += fileDirection, rank += rankDirection) {
            Optional<Piece> pieceOpt = chessBoard.getPiece(TileConverter.convertToTile(file, rank));

            if (pieceOpt.isPresent()) return false;
        }

        return true;
    }

    private int getDirection(int from, int to) {
        int diff = to - from;
        return diff / Math.abs(diff);
    }
}
