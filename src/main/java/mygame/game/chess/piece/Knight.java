package mygame.game.chess.piece;

import mygame.point.Point;

public class Knight extends ChessPiece {

    public Knight(Point point, String teamName) {
        super(point, teamName);
    }

    @Override
    public String toString() {
        return getTeamName().substring(0, 1) + " N";
    }
}
