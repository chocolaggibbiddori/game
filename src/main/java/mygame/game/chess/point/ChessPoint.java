package mygame.game.chess.point;

import mygame.point.Point;

public class ChessPoint extends Point {

    public ChessPoint(int x, int y) {
        super(x, y);
    }

    public ChessPoint(String strPoint) {
        super(strPoint);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChessPoint) {
            return strPoint.equals(((ChessPoint) obj).strPoint);
        }
        return false;
    }

    @Override
    protected String setStrPoint(int x, int y) {
        String before = String.valueOf(8 - x);
        String after = String.valueOf((char) ('a' + y));
        return after + before;
    }

    @Override
    protected int setX(String strPoint) {
        return '8' - strPoint.charAt(1);
    }

    @Override
    protected int setY(String strPoint) {
        return strPoint.charAt(0) - 'a';
    }
}
