package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public class Knight extends Piece {

    public Knight(Team team) {
        super(team);
    }

    @Override
    public boolean canMoveTo(Tile to) {
        Tile position = getPosition();
        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        return is3DiffTile(pFile, pRank, tFile, tRank) &&
               isNotSame(pFile, tFile) &&
               isNotSame(pRank, tRank);
    }

    private boolean is3DiffTile(int pFile, int pRank, int tFile, int tRank) {
        return absOfDiff(pFile, tFile) + absOfDiff(pRank, tRank) == 3;
    }

    private int absOfDiff(int i1, int i2) {
        return Math.abs(i1 - i2);
    }

    private boolean isNotSame(int i1, int i2) {
        return i1 != i2;
    }

    @Override
    public char getInitial() {
        return 'N';
    }
}
