package mygame.point;

public abstract class Point {

    protected int x;
    protected int y;
    protected String strPoint;

    protected Point(int x, int y) {
        this.x = x;
        this.y = y;
        strPoint = setStrPoint(x, y);
    }

    protected Point(String strPoint) {
        String lowerStrPoint = strPoint.toLowerCase();
        this.strPoint = lowerStrPoint;
        x = setX(lowerStrPoint);
        y = setY(lowerStrPoint);
    }

    protected abstract String setStrPoint(int x, int y);

    protected abstract int setX(String strPoint);

    protected abstract int setY(String strPoint);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getStrPoint() {
        return strPoint;
    }

    @Override
    public String toString() {
        return strPoint;
    }
}
