package mygame.game.chess.piece;

import mygame.game.chess.name.Name;
import mygame.piece.Piece;
import mygame.point.Point;

public class Pawn extends Piece {

    private final int moveDirect = teamName.equals(Name.TEAM_WHITE) ? -1 : 1;
    private boolean firstMove = true;
    private int moveCount = 0;

    public Pawn(Point point, String teamName) {
        super(point, teamName);
    }

    public void setFirstMoveToFalse() {
        firstMove = false;
    }

    public void addMoveCount() {
        moveCount++;
    }

    public int getMoveDirect() {
        return moveDirect;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
