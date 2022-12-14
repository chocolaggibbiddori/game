package mygame.point;

public class Point {

    private int x;
    private int y;
    private String strPoint;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        strPoint = setStrPoint(x, y);
    }

    public Point(String strPoint) {
        String lowerStrPoint = strPoint.toLowerCase();
        this.strPoint = lowerStrPoint;
        x = setX(lowerStrPoint);
        y = setY(lowerStrPoint);
    }

    private String setStrPoint(int x, int y) {
        String before = String.valueOf(8 - x);
        String after = String.valueOf('a' + y);
        return after + before;
    }

    private int setX(String strPoint) {
        return '8' - strPoint.charAt(1);
    }

    private int setY(String strPoint) {
        return strPoint.charAt(0) - 'a';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getStrPoint() {
        return strPoint;
    }
}
