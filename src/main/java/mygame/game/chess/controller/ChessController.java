package mygame.game.chess.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/chess")
public class ChessController {

    @GetMapping
    public String viewGame() {
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
