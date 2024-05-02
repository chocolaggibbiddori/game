package chocola.chess.piece;

import chocola.chess.Team;
import chocola.chess.Tile;

public final class Pawn extends MoveCheckedPiece {

    public static final int WHITE_PAWN_MOVE_DIRECT = 1;
    public static final int BLACK_PAWN_MOVE_DIRECT = -1;

    private final int moveDirect;

    public Pawn(Team team) {
        super(team);
        this.moveDirect = team == Team.WHITE ? WHITE_PAWN_MOVE_DIRECT : BLACK_PAWN_MOVE_DIRECT;
    }

    @Override
    public boolean canMoveTo(Tile to) {
        Tile position = getPosition();
        if (position == to) return false;

        int pFile = position.getFileIdx();
        int pRank = position.getRankIdx();
        int tFile = to.getFileIdx();
        int tRank = to.getRankIdx();

        return isMove(pFile, pRank, tFile, tRank) ||
               isAttack(pFile, pRank, tFile, tRank);
    }

    private boolean isMove(int pFile, int pRank, int tFile, int tRank) {
        return isDefaultMove(pFile, pRank, tFile, tRank) ||
               isDoubleStepMove(pFile, pRank, tFile, tRank);
    }

    private boolean isDefaultMove(int pFile, int pRank, int tFile, int tRank) {
        return pFile == tFile &&
               isMoveForward(pRank, tRank, false);
    }

    private boolean isDoubleStepMove(int pFile, int pRank, int tFile, int tRank) {
        return !isMoved() &&
               pFile == tFile &&
               isMoveForward(pRank, tRank, true);
    }

    private boolean isAttack(int pFile, int pRank, int tFile, int tRank) {
        return absOfDiff(pFile, tFile) == 1 && isMoveForward(pRank, tRank, false);
    }

    private int absOfDiff(int i1, int i2) {
        return Math.abs(i1 - i2);
    }

    private boolean isMoveForward(int pRank, int tRank, boolean doubled) {
        int step = doubled ? 2 : 1;
        return pRank + moveDirect * step == tRank;
    }

    @Override
    public String getInitial() {
        return "";
    }
}
