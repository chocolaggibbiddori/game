package mygame.game.chess.piece;

import mygame.game.chess.name.Name;
import mygame.point.Point;

public class Pawn extends ChessPiece {

    private final int moveDirect = getTeamName().equals(Name.TEAM_WHITE) ? -1 : 1;
    private boolean firstMove = true;

    public Pawn(Point point, String teamName) {
        super(point, teamName);
    }

    public void setFirstMoveToFalse() {
        firstMove = false;
    }

    public int getMoveDirect() {
        return moveDirect;
    }

    public boolean isFirstMove() {
        return firstMove;
    }
}
