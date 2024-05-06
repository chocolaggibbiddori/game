package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public final class King extends MoveCheckedPiece {

    public King(Team team, Tile position) {
        this(team, position, false);
    }

    private King(Team team, Tile position, boolean moved) {
        super(team, position, moved);
    }

    @Override
    public boolean canMoveTo(Tile to) {
        Tile position = getPosition();
        if (position == to) return false;

        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        return is1DiffStraight(pFile, pRank, tFile, tRank) ||
               is1DiffDiagonal(pFile, pRank, tFile, tRank) ||
               isCastling(position, to);
    }

    private boolean is1DiffStraight(int pFile, int pRank, int tFile, int tRank) {
        return (pFile == tFile && absOfDiff(pRank, tRank) == 1) ||
               (pRank == tRank && absOfDiff(pFile, tFile) == 1);
    }

    private boolean is1DiffDiagonal(int pFile, int pRank, int tFile, int tRank) {
        return isDiagonal(pFile, pRank, tFile, tRank) &&
               is1Diff(pFile, tFile);
    }

    private boolean is1Diff(int i1, int i2) {
        return absOfDiff(i1, i2) == 1;
    }

    private boolean isDiagonal(int pFile, int pRank, int tFile, int tRank) {
        return absOfDiff(pFile, tFile) == absOfDiff(pRank, tRank);
    }

    private int absOfDiff(int i1, int i2) {
        return Math.abs(i1 - i2);
    }

    private boolean isCastling(Tile position, Tile to) {
        if (isMoved()) return false;

        if (team == Team.WHITE) return position == Tile.E1 && (to == Tile.C1 || to == Tile.G1);
        return position == Tile.E8 && (to == Tile.C8 || to == Tile.G8);
    }

    @Override
    public String getInitial() {
        return "K";
    }

    @Override
    public King copy() {
        return new King(team, getPosition(), isMoved());
    }
}
