package mygame.game.chess.board;

import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.game.chess.piece.*;
import mygame.point.Point;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChessBoard implements Board {

    private static final Piece[][] board = new Piece[8][8];

    private static final String WHITE = "White";
    private static final String BLACK = "Black";

    @Override
    public void init() {
        setPawn();
        setRook();
        setKnight();
        setBishop();
        setQueen();
        setKing();
        log.info("Game Init");
    }

    private void setPawn() {
        Point[] twoLine = setPawnPointArray(2);
        Point[] sevenLine = setPawnPointArray(7);
        setPawn(twoLine, WHITE);
        setPawn(sevenLine, BLACK);
    }

    private Point[] setPawnPointArray(int row) {
        Point[] points = new Point[8];
        char a = 'a';
        for (int i = 0; i < points.length; i++) {
            String strPoint = String.valueOf(a + i) + i;
            Point point = new Point(strPoint);
            points[i] = point;
        }
        return points;
    }

    private void setPawn(Point[] points, String teamName) {
        for (Point point : points) {
            Pawn pawn = new Pawn(point, teamName);
            setInBoard(pawn, point);
        }
    }

    private void setInBoard(Piece piece, Point point) {
        int x = point.getX();
        int y = point.getY();
        board[x][y] = piece;
    }

    private void setRook() {
        Point point1 = new Point("a1");
        Point point2 = new Point("h1");
        Point point3 = new Point("a8");
        Point point4 = new Point("h8");
        Rook rook1 = new Rook(point1, WHITE);
        Rook rook2 = new Rook(point2, WHITE);
        Rook rook3 = new Rook(point3, BLACK);
        Rook rook4 = new Rook(point4, BLACK);
        setInBoard(rook1, point1);
        setInBoard(rook2, point2);
        setInBoard(rook3, point3);
        setInBoard(rook4, point4);
    }

    private void setKnight() {
        Point point1 = new Point("b1");
        Point point2 = new Point("g1");
        Point point3 = new Point("b8");
        Point point4 = new Point("g8");
        Knight knight1 = new Knight(point1, WHITE);
        Knight knight2 = new Knight(point2, WHITE);
        Knight knight3 = new Knight(point3, BLACK);
        Knight knight4 = new Knight(point4, BLACK);
        setInBoard(knight1, point1);
        setInBoard(knight2, point2);
        setInBoard(knight3, point3);
        setInBoard(knight4, point4);
    }

    private void setBishop() {
        Point point1 = new Point("c1");
        Point point2 = new Point("f1");
        Point point3 = new Point("c8");
        Point point4 = new Point("f8");
        Bishop bishop1 = new Bishop(point1, WHITE);
        Bishop bishop2 = new Bishop(point2, WHITE);
        Bishop bishop3 = new Bishop(point3, BLACK);
        Bishop bishop4 = new Bishop(point4, BLACK);
        setInBoard(bishop1, point1);
        setInBoard(bishop2, point2);
        setInBoard(bishop3, point3);
        setInBoard(bishop4, point4);
    }

    private void setQueen() {
        Point point1 = new Point("d1");
        Point point2 = new Point("d8");
        Queen queen1 = new Queen(point1, WHITE);
        Queen queen2 = new Queen(point2, BLACK);
        setInBoard(queen1, point1);
        setInBoard(queen2, point2);
    }

    private void setKing() {
        Point point1 = new Point("e1");
        Point point2 = new Point("e8");
        King king1 = new King(point1, WHITE);
        King king2 = new King(point2, BLACK);
        setInBoard(king1, point1);
        setInBoard(king2, point2);
    }
}
