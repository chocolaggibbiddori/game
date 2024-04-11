package chocola.omok;

class OmokBoard {

    private static final int LINE_NUMBER = 16;
    private static final String INIT_BOARD_STRING = """
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    000000000000000
                                                    """;

    private final StringBuilder boardStringBuilder;
    private OmokPoint lastPoint;

    OmokBoard() {
        boardStringBuilder = new StringBuilder(INIT_BOARD_STRING);
    }

    @Override
    public String toString() {
        return boardStringBuilder.toString();
    }

    OmokPoint getLastPoint() {
        return lastPoint;
    }

    char getBy(OmokPoint point) {
        int x = point.x();
        int y = point.y();
        return getBy(x, y);
    }

    char getBy(int x, int y) {
        int idx = getIdx(x, y);
        return boardStringBuilder.charAt(idx);
    }

    boolean isExist(int x, int y) {
        return isBlack(x, y) || isWhite(x, y);
    }

    boolean isBlack(int x, int y) {
        return checkValue(x, y, '1');
    }

    boolean isWhite(int x, int y) {
        return checkValue(x, y, '2');
    }

    private boolean checkValue(int x, int y, char value) {
        return getBy(x, y) == value;
    }

    void placeStone(Team team, OmokPoint point) {
        int x = point.x();
        int y = point.y();
        int idx = getIdx(x, y);
        boardStringBuilder.replace(idx, idx + 1, team.toStoneString());
        lastPoint = point;
    }

    private int getIdx(int x, int y) {
        return --x * LINE_NUMBER + --y;
    }
}
