package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public final class Bishop extends Piece {

    public Bishop(Team team) {
        super(team);
    }

    public boolean canMoveTo(Tile to) {
        Tile position = getPosition();
        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        return absOfDiff(pFile, tFile) == absOfDiff(pRank, tRank);
    }

    private int absOfDiff(int pFile, int tFile) {
        return Math.abs(pFile - tFile);
    }

    public char getInitial() {
        return 'B';
    }
}
