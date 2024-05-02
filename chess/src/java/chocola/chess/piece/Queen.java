package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public class Queen extends Piece {

    public Queen(Team team) {
        super(team);
    }

    @Override
    public boolean canMoveTo(Tile to) {
        Tile position = getPosition();
        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        return isStraight(pFile, pRank, tFile, tRank) || isDiagonal(pFile, pRank, tFile, tRank);
    }

    private boolean isStraight(int pFile, int pRank, int tFile, int tRank) {
        return pFile == tFile || pRank == tRank;
    }

    private boolean isDiagonal(int pFile, int pRank, int tFile, int tRank) {
        return absOfDiff(pFile, tFile) == absOfDiff(pRank, tRank);
    }

    private int absOfDiff(int i1, int i2) {
        return Math.abs(i1 - i2);
    }

    @Override
    public char getInitial() {
        return 'Q';
    }
}
