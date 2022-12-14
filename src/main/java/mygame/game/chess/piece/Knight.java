package mygame.game.chess.piece;

import mygame.point.Point;

public class Knight extends Piece{

    public Knight(Point point, String teamName) {
        super(point, teamName);
    }

    @Override
    public String toString() {
        return teamName.substring(0, 1) + " N";
    }
}
