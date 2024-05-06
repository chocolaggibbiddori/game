package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public final class Bishop extends Piece {

    public Bishop(Team team, Tile position) {
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

        return absOfDiff(pFile, tFile) == absOfDiff(pRank, tRank);
    }

    private int absOfDiff(int i1, int i2) {
        return Math.abs(i1 - i2);
    }

    @Override
    public String getInitial() {
        return "B";
    }

    @Override
    public Bishop copy() {
        return new Bishop(team, getPosition());
    }
}
