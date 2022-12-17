package mygame.game.chess.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mygame.board.Board;
import mygame.turn.Turn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chess")
public class ChessController {

    private final Board chessBoard;
    private final Turn chessTurn;

    @GetMapping
    public String viewGame() {
        chessBoard.init();
        log.info("Game Start");
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
