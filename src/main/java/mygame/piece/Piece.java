package mygame.piece;

import mygame.point.Point;

public abstract class Piece {

    protected Point point;
    protected final String teamName;

    public Piece(Point point, String teamName) {
        this.point = point;
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public String toString() {
        return teamName.substring(0, 1) + " " + getClass().getSimpleName().substring(0, 1);
    }
}
