package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public final class Rook extends MoveCheckedPiece {

    public Rook(Team team) {
        super(team);
    }

    @Override
    public boolean canMoveTo(Tile to) {
        Tile position = getPosition();
        if (position == to) return false;

        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        return pFile == tFile || pRank == tRank;
    }

    @Override
    public char getInitial() {
        return 'R';
    }
}
