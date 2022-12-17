package mygame.game.chess.controller;

import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.game.chess.point.ChessPoint;
import mygame.game.chess.validation.ChessValidation;
import mygame.piece.Piece;
import mygame.turn.Turn;
import mygame.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/chess")
public class ChessController {

    private final Board chessBoard;
    private final Turn chessTurn;
    private final ChessValidation chessValidation;
    private final View view;

    @Autowired
    public ChessController(Board chessBoard, Turn chessTurn, ChessValidation chessValidation) {
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

        model.addAttribute("view", view.drawBoard());
        return "chess/select";
    }

    @PostMapping("/select")
    public String select(@RequestParam String strPoint, Model model) {
        strPoint = strPoint.trim().toLowerCase();
        if (!chessValidation.checkStringPoint(strPoint)) {
            model.addAttribute("view", view.drawBoard())
                    .addAttribute("error", "잘못된 입력입니다.")
                    .addAttribute("hasError", true);
            return "chess/select";
        }

        ChessPoint point = new ChessPoint(strPoint);
        if (chessValidation.isNull(point)) {
            model.addAttribute("view", view.drawBoard())
                    .addAttribute("error", "빈 칸입니다.")
                    .addAttribute("hasError", true);
            return "chess/select";
        }

        Piece piece = chessBoard.findByPoint(point);
        if (!chessValidation.isOurTeam(piece)) {
            model.addAttribute("view", view.drawBoard())
                    .addAttribute("error", chessTurn.getCurrentTeam() + " Team 차례입니다.")
                    .addAttribute("hasError", true);
            return "chess/select";
        }


        return "chess/move";
    }

    @PostMapping("/move")
    public String move() {
        return "chess/select";
    }
}
