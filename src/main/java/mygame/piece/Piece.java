package mygame.piece;

import mygame.point.Point;

public abstract class Piece {

    private Point point;
    private final String teamName;

    public Piece(Point point, String teamName) {
        this.point = point;
        this.teamName = teamName;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public String toString() {
        return teamName.substring(0, 1) + " " + getClass().getSimpleName().substring(0, 1);
    }
}
