package mygame.game.chess.controller;

import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.turn.Turn;
import mygame.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/chess")
public class ChessController {

    private final Board chessBoard;
    private final Turn chessTurn;
    private final View view;

    @Autowired
    public ChessController(Board chessBoard, Turn chessTurn) {
        this.chessBoard = chessBoard;
        this.chessTurn = chessTurn;
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
    public String select() {
        return "chess/move";
    }

    @PostMapping("/move")
    public String move() {
        return "chess/select";
    }
}
