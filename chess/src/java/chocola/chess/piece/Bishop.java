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

        return Math.abs(pFile - tFile) == Math.abs(pRank - tRank);
    }

    public char getInitial() {
        return 'B';
    }
}
