package mygame.view;

import mygame.board.Board;
import mygame.piece.Piece;
import mygame.point.Point;

import java.util.List;

public class View {

    private final Board board;
    private final String[] rowNumber = {"8", "7", "6", "5", "4", "3", "2", "1"};
    private final String[] colAlphabet = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private StringBuilder sb;
    private int rowNumberIdx;

    public View(Board board) {
        this.board = board;
    }

    public String drawBoard() {
        sb = new StringBuilder();
        rowNumberIdx = 0;
        sb.append("<br>\n");
        drawAlphabet();
        for (int i = 0; i < 4; i++) {
            drawRowLine();
            drawColLineFirstWhite();
            drawColLineWithPieceFirstWhite();
            drawColLineFirstWhite();
            drawRowLine();
            drawColLineFirstBlack();
            drawColLineWithPieceFirstBlack();
            drawColLineFirstBlack();
        }
        drawRowLine();
        drawAlphabet();
        return sb.toString();
    }

    public String drawBoard(List<Point> moveList) {
        sb = new StringBuilder();
        rowNumberIdx = 0;
        sb.append("<br>\n");
        drawAlphabet();
        for (int i = 0; i < 4; i++) {
            drawRowLine();
            drawColLineFirstWhite();
            drawColLineWithPieceFirstWhite(moveList);
            drawColLineFirstWhite();
            drawRowLine();
            drawColLineFirstBlack();
            drawColLineWithPieceFirstBlack(moveList);
            drawColLineFirstBlack();
        }
        drawRowLine();
        drawAlphabet();
        return sb.toString();
    }

    private void drawAlphabet() {
        sb.append(" ".repeat(12));
        for (String alphabet : colAlphabet) {
            sb.append("<b>").append(alphabet).append("</b>").append(" ".repeat(9));
        }
        sb.append("<br>\n");
    }

    private void drawRowLine() {
        sb.append(" ".repeat(7));
        sb.append(" ---------".repeat(8));
        sb.append("<br>\n");
    }

    private void drawColLineFirstWhite() {
        String col = "|" + " ".repeat(9) + "|---------";
        sb.append(" ".repeat(7));
        sb.append(col.repeat(4));
        sb.append("|");
        sb.append("<br>\n");
    }

    private void drawColLineFirstBlack() {
        String col = "|---------|" + " ".repeat(9);
        sb.append(" ".repeat(7));
        sb.append(col.repeat(4));
        sb.append("|");
        sb.append("<br>\n");
    }

    private void drawColLineWithPieceFirstWhite() {
        sb.append("   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx]).append("</b>");
        sb.append("   ");
        Piece piece;
        for (int i = 0; i < colAlphabet.length; i += 2) {
            sb.append("|");
            sb.append("   ");
            piece = board.findByPoint(rowNumberIdx, i);
            drawPiece(piece);
            sb.append("   |---");
            piece = board.findByPoint(rowNumberIdx, i + 1);
            drawPiece(piece);
            sb.append("---");
        }
        sb.append("|   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx++]).append("</b>");
        sb.append("<br>\n");
    }

    private void drawColLineWithPieceFirstWhite(List<Point> moveList) {
        sb.append("   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx]).append("</b>");
        sb.append("   ");
        Piece piece;
        for (int i = 0; i < colAlphabet.length; i += 2) {
            sb.append("|");
            sb.append("   ");
            piece = board.findByPoint(rowNumberIdx, i);
            drawPiece(piece, moveList, i);
            sb.append("   |---");
            piece = board.findByPoint(rowNumberIdx, i + 1);
            drawPiece(piece, moveList, i + 1);
            sb.append("---");
        }
        sb.append("|   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx++]).append("</b>");
        sb.append("<br>\n");
    }

    private void drawPiece(Piece piece) {
        if (piece == null) {
            sb.append("   ");
        } else {
            sb.append("<b>").append(piece).append("</b>");
        }
    }

    private void drawPiece(Piece piece, List<Point> moveList, int yIdx) {
        if (moveList.size() == 0) {
            drawPiece(piece);
            return;
        }

        if (piece == null) {
            if (isContainsPoint(moveList, rowNumberIdx, yIdx)) {
                sb.append(" o ");
            }else {
                sb.append("   ");
            }
        } else {
            if (isContainsPoint(moveList, rowNumberIdx, yIdx)) {
                sb.append("<b>")
                        .append(piece.toString().substring(0, 1))
                        .append("o")
                        .append(piece.toString().substring(2))
                        .append("</b>");
            } else {
                sb.append("<b>").append(piece).append("</b>");
            }
        }
    }

    private boolean isContainsPoint(List<Point> moveList, int x, int y) {
        for (Point point : moveList) {
            if (point.getX() != x) return false;
            if (point.getY() != y) return false;
        }
        return true;
    }

    private void drawColLineWithPieceFirstBlack() {
        sb.append("   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx]).append("</b>");
        sb.append("   ");
        Piece piece;
        for (int i = 0; i < colAlphabet.length; i += 2) {
            sb.append("|---");
            piece = board.findByPoint(rowNumberIdx, i);
            drawPiece(piece);
            sb.append("---|   ");
            piece = board.findByPoint(rowNumberIdx, i + 1);
            drawPiece(piece);
            sb.append("   ");
        }
        sb.append("|   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx++]).append("</b>");
        sb.append("<br>\n");
    }

    private void drawColLineWithPieceFirstBlack(List<Point> moveList) {
        sb.append("   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx]).append("</b>");
        sb.append("   ");
        Piece piece;
        for (int i = 0; i < colAlphabet.length; i += 2) {
            sb.append("|---");
            piece = board.findByPoint(rowNumberIdx, i);
            drawPiece(piece, moveList, i);
            sb.append("---|   ");
            piece = board.findByPoint(rowNumberIdx, i + 1);
            drawPiece(piece, moveList, i + 1);
            sb.append("   ");
        }
        sb.append("|   ");
        sb.append("<b>").append(rowNumber[rowNumberIdx++]).append("</b>");
        sb.append("<br>\n");
    }
}
