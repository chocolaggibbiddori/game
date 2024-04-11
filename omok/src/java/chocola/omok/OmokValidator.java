package chocola.omok;

import chocola.interfaces.Validator;

class OmokValidator implements Validator<String> {

    private final OmokBoard board;

    OmokValidator(OmokBoard omokBoard) {
        board = omokBoard;
    }

    @Override
    public boolean isValid(String input) {
        String[] split = input.split(",");
        if (split.length != 2) return false;

        String s1 = split[0].trim();
        String s2 = split[1].trim();

        try {
            int x = Integer.parseInt(s1);
            int y = Integer.parseInt(s2);

            return isValid(x, y);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValid(int x, int y) {
        return isValidRange(x, y) && !board.isExist(x, y);
    }

    private boolean isValidRange(int x, int y) {
        return isValidRange(x) && isValidRange(y);
    }

    private boolean isValidRange(int n) {
        return n >= 1 && n <= 15;
    }

    boolean isValidAtBlack(OmokPoint point) {
        int _33LineNum = 0;

        Direction[] directions = Direction.values();
        for (Direction direction : directions) {
            String line1 = getStraightLine(point, 4, 1, direction);
            String line2 = getStraightLine(point, 3, 2, direction);

            if (is33Line(line1) || is33Line(line2)) ++_33LineNum;
            if (_33LineNum == 2) return false;
        }

        return true;
    }

    private boolean is33Line(String line) {
        String[] forbiddenLines = {"001100", "010100", "011000", "001010", "010010"};

        for (String forbiddenLine : forbiddenLines)
            if (forbiddenLine.equals(line)) return true;

        return false;
    }

    Result getResult() {
        if (isDraw()) return Result.DRAW;

        OmokPoint lastPoint = board.getLastPoint();
        Direction[] directions = Direction.getHalfRound();
        int max = 4;

        for (Direction direction : directions) {
            String line = getStraightLine(lastPoint, max, direction);

            if (isWinner(line, Team.BLACK)) return Result.BLACK_WIN;
            else if (isWinner(line, Team.WHITE)) return Result.WHITE_WIN;
        }

        return Result.NONE;
    }

    private boolean isWinner(String line, Team team) {
        String winCond = team.toStoneString().repeat(5);
        int idx = line.indexOf(winCond);
        int lastIdx = line.lastIndexOf(winCond);

        return idx != -1 && idx == lastIdx;
    }

    private boolean isDraw() {
        for (char c : board.toString().toCharArray())
            if (c == '0') return false;

        return true;
    }

    private String getStraightLine(OmokPoint point, int max, Direction direction) {
        return getStraightLine(point, max, max, direction);
    }

    private String getStraightLine(OmokPoint point, int directionMax, int oppositeDirectionMax, Direction direction) {
        Direction oppositeDirection = direction.getOppositeDirection();

        String directionLine = getHalfStraightLine(point, directionMax, direction);
        String oppositeDirectionLine = getHalfStraightLine(point, oppositeDirectionMax, oppositeDirection);
        char currentStone = board.getBy(point);

        return requireReverse(direction) ?
                directionLine + currentStone + oppositeDirectionLine :
                oppositeDirectionLine + currentStone + directionLine;
    }

    private String getHalfStraightLine(OmokPoint point, int max, Direction direction) {
        StringBuilder sb = new StringBuilder(max);
        int x = point.x();
        int y = point.y();

        for (int i = x + direction.xDirection, j = y + direction.yDirection, cnt = 0;
             cnt < max && isValidRange(i, j);
             i += direction.xDirection, j += direction.yDirection, ++cnt) {
            sb.append(board.getBy(i, j));
        }

        return requireReverse(direction) ? sb.reverse().toString() : sb.toString();
    }

    private boolean requireReverse(Direction direction) {
        return switch (direction) {
            case UP, UP_RIGHT, RIGHT, DOWN_RIGHT -> false;
            case DOWN, DOWN_LEFT, LEFT, UP_LEFT -> true;
        };
    }

    private enum Direction {

        UP(-1, 0) {
            @Override
            Direction getOppositeDirection() {
                return DOWN;
            }
        },
        UP_RIGHT(-1, 1) {
            @Override
            Direction getOppositeDirection() {
                return DOWN_LEFT;
            }
        },
        RIGHT(0, 1) {
            @Override
            Direction getOppositeDirection() {
                return LEFT;
            }
        },
        DOWN_RIGHT(1, 1) {
            @Override
            Direction getOppositeDirection() {
                return UP_LEFT;
            }
        },
        DOWN(1, 0) {
            @Override
            Direction getOppositeDirection() {
                return UP;
            }
        },
        DOWN_LEFT(1, -1) {
            @Override
            Direction getOppositeDirection() {
                return UP_RIGHT;
            }
        },
        LEFT(0, -1) {
            @Override
            Direction getOppositeDirection() {
                return RIGHT;
            }
        },
        UP_LEFT(-1, -1) {
            @Override
            Direction getOppositeDirection() {
                return DOWN_RIGHT;
            }
        };

        final int xDirection;
        final int yDirection;

        Direction(int xDirection, int yDirection) {
            this.xDirection = xDirection;
            this.yDirection = yDirection;
        }

        static Direction[] getHalfRound() {
            return new Direction[]{UP, UP_RIGHT, RIGHT, DOWN_RIGHT};
        }

        abstract Direction getOppositeDirection();
    }
}
