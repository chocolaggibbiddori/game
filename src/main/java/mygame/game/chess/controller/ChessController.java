package mygame.game.chess.controller;

import lombok.extern.slf4j.Slf4j;
import mygame.game.chess.board.ChessBoard;
import mygame.game.chess.piece.ChessPiece;
import mygame.game.chess.piece.King;
import mygame.game.chess.piece.Pawn;
import mygame.game.chess.point.ChessPoint;
import mygame.game.chess.turn.ChessNotation;
import mygame.game.chess.turn.ChessTurn;
import mygame.game.chess.validation.ChessValidation;
import mygame.piece.Piece;
import mygame.game.chess.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/chess")
public class ChessController {

    private final ChessBoard chessBoard;
    private final ChessTurn chessTurn;
    private final ChessValidation chessValidation;
    private final View view;

    @Autowired
    public ChessController(ChessBoard chessBoard, ChessTurn chessTurn, ChessValidation chessValidation) {
        this.chessBoard = chessBoard;
        this.chessTurn = chessTurn;
        this.chessValidation = chessValidation;
        this.view = new View(chessBoard);
    }

    @GetMapping
    public String viewGame(Model model) {
        chessBoard.init();
        chessTurn.turnStart();
        log.info("Game Start");

        model.addAttribute("view", view.drawBoard())
                .addAttribute("turnCount", chessTurn.getCount())
                .addAttribute("notation", chessTurn.getStringNotation());
        return "chess/select";
    }

    @GetMapping("/select")
    public String select(Model model) {
        model.addAttribute("view", view.drawBoard())
                .addAttribute("turnCount", chessTurn.getCount())
                .addAttribute("notation", chessTurn.getStringNotation());

        return "chess/select";
    }

    @PostMapping("/select")
    public String select(@RequestParam String strPoint, Model model, RedirectAttributes redirectAttributes) {
        strPoint = strPoint.trim().toLowerCase();
        if (!chessValidation.isRightStringPoint(strPoint)) {
            addAttributeWithError(model, "잘못된 입력입니다.");
            return "chess/select";
        }

        ChessPoint point = new ChessPoint(strPoint);
        if (chessValidation.isNull(point)) {
            addAttributeWithError(model, "빈 칸입니다.");
            return "chess/select";
        }

        ChessPiece piece = (ChessPiece) chessBoard.findByPoint(point);
        if (!chessValidation.isOurTeam(piece)) {
            addAttributeWithError(model, chessTurn.getCurrentTeam() + " Team 차례입니다.");
            return "chess/select";
        }

        redirectAttributes.addAttribute("point", strPoint);
        return "redirect:/chess/{point}/move";
    }

    private void addAttributeWithError(Model model, String errorMessage) {
        log.error("errorMessage={}", errorMessage);
        model.addAttribute("view", view.drawBoard())
                .addAttribute("error", errorMessage)
                .addAttribute("hasError", true)
                .addAttribute("turnCount", chessTurn.getCount())
                .addAttribute("notation", chessTurn.getStringNotation());
    }

    @GetMapping("/{point}/move")
    public String showMoveList(@PathVariable ChessPoint point, Model model) {
        ChessPiece piece = (ChessPiece) chessBoard.findByPoint(point);
        List<ChessPoint> moveList = chessValidation.moveList(piece);
        log.info("select={}, {}", piece, point);

        model.addAttribute("view", view.drawBoard(moveList))
                .addAttribute("turnCount", chessTurn.getCount())
                .addAttribute("notation", chessTurn.getStringNotation());
        return "chess/move";
    }

    @PostMapping("/{startPoint}/move")
    public String move(@PathVariable ChessPoint startPoint, @RequestParam ChessPoint endPoint, Model model) {
        if (!chessValidation.isRightStringPoint(endPoint.getStrPoint())) {
            addAttributeWithError(model, "잘못된 입력입니다.");
            return "chess/select";
        }

        ChessPiece piece = (ChessPiece) chessBoard.findByPoint(startPoint);
        List<ChessPoint> moveList = chessValidation.moveList(piece);
        log.info("piece={}", piece);
        log.info("moveList={}", moveList);
        log.info("move to {}", endPoint);
        if (!moveList.contains(endPoint)) {
            addAttributeWithError(model, "해당 위치로 이동할 수 없습니다.");
            return "chess/select";
        }

        Piece deadPiece = chessBoard.move(piece, endPoint);
        if (deadPiece instanceof King) {
            model.addAttribute("view", view.drawBoard())
                    .addAttribute("victoryTeam", chessTurn.getCurrentTeam());
            return "chess/victory";
        }

        piece.addMoveCount();
        if (piece instanceof Pawn) {
            if (((Pawn) piece).isFirstMove()) {
                ((Pawn) piece).setFirstMoveToFalse();
            }
            if (isEnpassant((Pawn) piece)) {
                int count = chessTurn.getCount();
                ChessNotation notation = chessTurn.getRepository().getNotation(count - 1);
                chessBoard.remove(notation.getEndPoint());
            }
        }

        chessTurn.setNotation(piece, startPoint, endPoint);
        chessTurn.nextTurn();
        return "redirect:/chess/select";
    }

    private boolean isEnpassant(Pawn pawn) {
        int count = chessTurn.getCount();
        if (count < 2) {
            return false;
        }

        ChessNotation notation = chessTurn.getRepository().getNotation(count - 1);
        int x = pawn.getPoint().getX() - pawn.getMoveDirect();
        int y = pawn.getPoint().getY();
        return notation.getChessPiece() == chessBoard.findByPoint(x, y);
    }
}
