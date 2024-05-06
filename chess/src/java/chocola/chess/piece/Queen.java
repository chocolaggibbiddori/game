package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public final class Queen extends Piece {

    public Queen(Team team, Tile position) {
        super(team, position);
    }

    @Override
    public boolean canMoveTo(Tile to) {
        Tile position = getPosition();
        if (position == to) return false;

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
    public String getInitial() {
        return "Q";
    }

    @Override
    public Queen copy() {
        return new Queen(team, getPosition());
    }
}
